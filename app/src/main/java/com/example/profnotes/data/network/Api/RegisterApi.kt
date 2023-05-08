package com.example.profnotes.data.network.Api

import com.example.profnotes.data.models.SignInUser
import com.example.profnotes.model.request.LoginResponse
import com.example.profnotes.model.request.UserRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RegisterApi {
    @GET("test")
    suspend fun test(): Response<ResponseBody>

    @POST("signup")
    suspend fun signIn(
        @Body user: SignInUser
    ): Response<ResponseBody>

    @POST("signin")
    suspend fun logIn(
        @Body user: UserRequest
    ): LoginResponse
}