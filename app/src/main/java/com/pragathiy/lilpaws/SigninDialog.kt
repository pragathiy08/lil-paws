package com.pragathiy.lilpaws

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class SigninDialog : DialogFragment() {

    private lateinit var listener: SigninDialogListener

    interface SigninDialogListener {
        fun onSignin(username: String, password: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as SigninDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement SigninDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            MaterialAlertDialogBuilder(it).apply {
                val inflater = it.layoutInflater.inflate(R.layout.layout_signin, null)
                setView(inflater)
                setCancelable(false)
                setPositiveButton("Signin") { dialog, _ ->
                    val username =
                        inflater.findViewById<TextInputEditText>(R.id.input_signin_username)
                    val password =
                        inflater.findViewById<TextInputEditText>(R.id.input_signin_password)
                    listener.onSignin(
                        username = username.text.toString(),
                        password = password.text.toString()
                    )
                    dialog.dismiss()
                }
                setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }
            }.create()
        } ?: throw IllegalStateException("Activity can't be null")
    }
}