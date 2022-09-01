package com.example.profnotes.data.repo.core

import com.example.profnotes.data.models.util.ResponseWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

open class BaseRepository {
    suspend inline fun <T> getResponseInResponseWrapper(
        response: Response<T>
    ): ResponseWrapper<T> {
        return withContext(Dispatchers.IO) {
            if (response.isSuccessful) {
                ResponseWrapper.Success(
                    body = response.body()!!
                )
            } else {
                ResponseWrapper.Error(
                    code = response.code()
                )
            }
        }
    }
}