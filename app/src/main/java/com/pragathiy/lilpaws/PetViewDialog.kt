package com.pragathiy.lilpaws

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class PetViewDialog(private val pet: Pet, private val isMyPet: Boolean) : DialogFragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var listener: PetViewListener

    interface PetViewListener {
        fun onDeletePet()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as PetViewListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement SigninDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        activity?.let {
            auth = FirebaseAuth.getInstance()
            MaterialAlertDialogBuilder(it).apply {
                // Get inflater
                val inflater = it.layoutInflater.inflate(R.layout.pet_view_layout, null)

                // Set headline
                val headline = inflater.findViewById<MaterialTextView>(R.id.viewer_headline)
                headline.text =
                    getString(R.string.text_name).replace("\$name", pet.name!!)

                // Set subtitle
                val subtitle = inflater.findViewById<MaterialTextView>(R.id.viewer_subtitle)
                subtitle.text =
                    getString(R.string.text_description)
                        .replace("\$age", pet.age!!.toString(10))
                        .replace("\$type", pet.type!!.toLowerCase(Locale.ROOT))

                // Set email chip
                val emailChip = inflater.findViewById<Chip>(R.id.viewer_chip_email)
                emailChip.text = getString(R.string.chip_email)
                    .replace("\$email", pet.owner!!, false)

                // Set delete button
                val deleteButton = inflater.findViewById<MaterialButton>(R.id.viewer_button_delete)
                if (!isMyPet) {
                    deleteButton.visibility = View.INVISIBLE
                }
                deleteButton.setOnClickListener {
                    listener.onDeletePet()
                    this@PetViewDialog.dialog?.dismiss()
                }

                setView(inflater)
                setCancelable(true)
            }.create()
        } ?: throw IllegalStateException("Activity can't be null")
}