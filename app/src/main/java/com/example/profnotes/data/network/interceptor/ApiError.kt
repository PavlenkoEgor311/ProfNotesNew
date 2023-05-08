package com.example.profnotes.data.network.interceptor

import com.squareup.moshi.JsonClass
import java.io.IOException

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val status: Int,
    val message: String,
    val detail: String?,
    val validationError: ValidationError,
)

data class ValidationError(
    val query: List<ErrorQuery>,
    val body: List<ErrorBody>,
)

data class ErrorQuery(
    val message: String?,
    val name: String,
)

data class ErrorBody(
    val message: String?,
    val name: String,
)

sealed class ApiError(
    override val message: String,
    val errorResponse: ErrorResponse?
) : IOException(message) {

    class NotFound(
        message: String?,
        errorResponse: ErrorResponse?
    ) : ApiError(message ?: "Not Found", errorResponse)

    class UserDoesNotExist(
        message: String?,
        errorResponse: ErrorResponse?
    ) : ApiError(message ?: "User does not exist", errorResponse)

    class BadRequest(
        message: String?,
        errorResponse: ErrorResponse?
    ) : ApiError(message ?: "Bad request", errorResponse)

    class TokenInvalidError(
        message: String?,
        errorResponse: ErrorResponse?
    ) : ApiError(message ?: "Token invalid error", errorResponse)

    class NetworkError(
        message: String?,
        errorResponse: ErrorResponse?
    ) : ApiError(message ?: "Network not available", errorResponse)

    class UnknownError(
        message: String?,
        errorResponse: ErrorResponse?
    ) : ApiError(message ?: "Unknown error", errorResponse)
}