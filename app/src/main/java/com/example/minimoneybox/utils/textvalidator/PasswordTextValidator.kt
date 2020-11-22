package com.example.minimoneybox.utils.textvalidator

import android.content.Context
import android.text.Editable
import com.example.minimoneybox.R
import com.google.android.material.textfield.TextInputLayout

class PasswordTextValidator(context: Context, textInputLayout: TextInputLayout) :
    EditTextValidator(context, textInputLayout) {


    override fun afterTextChanged(text: Editable) {
        when {
            text.isEmpty() -> setError(R.string.error_required_field)
            text.length < MINIMUM_PASSWORD_LENGTH -> setError(R.string.error_password_short)
            !containsUpperLetter(text.toString()) -> setError(R.string.error_password_need_upper_letter)
            !containsLowerLetter(text.toString()) -> setError(R.string.error_password_need_lower_letter)
            !containsDigit(text.toString()) -> setError(R.string.error_password_need_upper_digit)
            else -> hideError()
        }
    }

    private fun containsUpperLetter(string: String): Boolean = ".*[A-Z].*".toRegex().matches(string)

    private fun containsLowerLetter(string: String): Boolean = ".*[a-z].*".toRegex().matches(string)

    private fun containsDigit(string: String): Boolean = ".*[0-9].*".toRegex().matches(string)

    companion object {
        private const val MINIMUM_PASSWORD_LENGTH = 8
    }

}