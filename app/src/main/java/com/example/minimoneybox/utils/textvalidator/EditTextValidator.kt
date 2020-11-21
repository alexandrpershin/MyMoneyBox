package com.example.minimoneybox.utils.textvalidator

import android.content.Context
import android.text.TextWatcher
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout

abstract class EditTextValidator(val context: Context, val textInputLayout: TextInputLayout) :
    TextWatcher {
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    fun setError(@StringRes stringResId: Int) {
        textInputLayout.isErrorEnabled = true
        textInputLayout.error = context.getString(stringResId)
    }

    fun hideError() {
        textInputLayout.isErrorEnabled = false
    }

}