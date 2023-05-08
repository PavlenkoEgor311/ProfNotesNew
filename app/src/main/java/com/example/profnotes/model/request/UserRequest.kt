package com.example.profnotes.model.request

data class UserRequest(
    val login: String,
    val password: String,
)

data class LoginResponse(
    val token: String,
    val idUser: Long,
)
