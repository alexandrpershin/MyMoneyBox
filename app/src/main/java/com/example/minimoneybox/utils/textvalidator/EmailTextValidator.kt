package com.example.minimoneybox.utils.textvalidator

import android.content.Context
import android.text.Editable
import com.example.minimoneybox.R
import com.google.android.material.textfield.TextInputLayout

class EmailTextValidator(context: Context, textInputLayout: TextInputLayout) :
    EditTextValidator(context, textInputLayout) {

    private val emailValidator = EmailValidatorImp()

    override fun afterTextChanged(text: Editable) {
        when {
            text.isEmpty() -> setError(R.string.error_required_field)
            !emailValidator.isValid(text.toString()) -> setError(R.string.email_address_error)
            else -> hideError()
        }
    }

}