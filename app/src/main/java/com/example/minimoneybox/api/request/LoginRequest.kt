package com.example.minimoneybox.api.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoginRequest(
    @SerializedName("Email") val login: String,
    @SerializedName("Password") val password: String,
    @SerializedName("Name") val name: String = ""
) : Serializable