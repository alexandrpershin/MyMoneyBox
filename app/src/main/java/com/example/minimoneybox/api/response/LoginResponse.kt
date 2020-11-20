package com.example.minimoneybox.api.response

open class LoginResponse(
    val session: Session = Session(),
    val user: UserDto = UserDto()
)

data class Session(
    val token: String = ""
)

data class UserDto(
    val email: String = "",
    val firstName: String = "",
    val lastName: String = ""
)
