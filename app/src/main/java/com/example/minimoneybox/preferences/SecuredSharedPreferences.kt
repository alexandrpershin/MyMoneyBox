package com.example.minimoneybox.preferences


interface SecuredSharedPreferences {
    var token: String

    var isLoggedIn: Boolean

    fun clear()
}