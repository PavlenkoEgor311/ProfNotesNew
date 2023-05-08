package com.example.profnotes.data.network.interceptor

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class PrettyLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        val logName = "okhttpClient"
        if (message.startsWith("{") || message.startsWith("[")) {
            try {
                val prettyPrintJson = GsonBuilder().setPrettyPrinting()
                    .create().toJson(JsonParser().parse(message))
                Timber.tag(logName).d(prettyPrintJson)
            } catch (m: JsonSyntaxException) {
                Timber.tag(logName).d(message)
            }
        } else {
            Timber.tag(logName).d(message)
            return
        }
    }
}