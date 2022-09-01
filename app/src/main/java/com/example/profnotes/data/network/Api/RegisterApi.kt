package com.example.profnotes.data.network.Api

import com.example.profnotes.data.models.AuthBody
import com.example.profnotes.data.models.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {
    @POST("register")
    fun postRegister(
        @Body authInfo:AuthBody
    ):RegisterResponse
}