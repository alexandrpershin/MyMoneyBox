package com.example.minimoneybox.utils

import android.util.Patterns

interface EmailValidator {
    fun isValid(email : String) : Boolean
}

class EmailValidatorImp : EmailValidator {

    override fun isValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.toRegex().matches(email)
    }

}