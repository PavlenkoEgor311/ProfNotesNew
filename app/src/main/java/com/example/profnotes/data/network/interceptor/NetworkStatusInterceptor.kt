package com.example.profnotes.data.network.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response

interface NetworkConnectivity {
    fun isConnected(): Boolean
}

class NetworkMonitor(
    private val context: Context,
) : NetworkConnectivity {
    override fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        } ?: false
    }
}


class NetworkStatusInterceptor(
    private val networkMonitor: NetworkConnectivity
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkMonitor.isConnected()) throw ApiError.NetworkError(
            "Network not available",
            null
        )
        return chain.proceed(chain.request())
    }
}

