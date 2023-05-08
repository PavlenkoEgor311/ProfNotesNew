package com.example.profnotes.data.network.interceptor

import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class ErrorStatusInterceptor @Inject constructor(
    private val moshi: Moshi,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())

        if (response.isSuccessful) return response

        val errorResponse = runCatching {
            moshi.adapter(ErrorResponse::class.java).lenient().fromJson(response.body!!.string())
        }.getOrNull()

        val errMessage = errorResponse?.message ?: "Unknown error"
        Timber.e(errMessage)
        Timber.e(response.code.toString())
        Timber.e(response.toString())

        when (response.code) {
            401 -> throw ApiError.TokenInvalidError(errMessage, errorResponse)
            404 -> throw ApiError.NotFound(errMessage, errorResponse)
            409 -> throw ApiError.NotFound(errMessage, errorResponse)
            400 -> throw ApiError.BadRequest(errMessage, errorResponse)
            403 -> throw ApiError.UserDoesNotExist(errMessage, errorResponse)
            else -> throw ApiError.UnknownError(errMessage, errorResponse)
        }
    }
}