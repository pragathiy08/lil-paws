package com.pragathiy.lilpaws

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class SignupDialog : DialogFragment() {

    private lateinit var listener: SignupDialogListener

    interface SignupDialogListener {
        fun onSignup(username: String, password: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as SignupDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement SignupDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            MaterialAlertDialogBuilder(it).apply {
                val inflater = it.layoutInflater.inflate(R.layout.layout_signin, null)
                setView(inflater)
                setCancelable(false)
                setPositiveButton(R.string.button_signup) { dialog, _ ->
                    val username =
                        inflater.findViewById<TextInputEditText>(R.id.input_signin_username)
                    val password =
                        inflater.findViewById<TextInputEditText>(R.id.input_signin_password)
                    listener.onSignup(
                        username = username.text.toString(),
                        password = password.text.toString()
                    )
                    dialog.dismiss()
                }
                setNegativeButton(R.string.button_cancel) { dialog, _ ->
                    dialog.cancel()
                }
            }.create()
        } ?: throw IllegalStateException("Activity can't be null")
    }
}