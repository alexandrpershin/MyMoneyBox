package com.example.minimoneybox.api.response

import com.google.gson.annotations.SerializedName

open class LoginResponse(
    @SerializedName("Session") val session: Session = Session(),
    @SerializedName("User") val user: UserDto = UserDto()
)

data class Session(
    @SerializedName("BearerToken") val bearerToken: String = ""
)

data class UserDto(
    @SerializedName("Email") val email: String = "",
    @SerializedName("FirstName") val firstName: String = "",
    @SerializedName("LastName") val lastName: String = ""
)
