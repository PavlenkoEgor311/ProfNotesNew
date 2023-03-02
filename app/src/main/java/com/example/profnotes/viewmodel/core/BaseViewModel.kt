package com.example.profnotes.viewmodel.core

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _evenState = MutableStateFlow<Event>(Event.Idle)
    val evenState = _evenState.asStateFlow()

    protected fun setIsLoading(value: Boolean) {
        _isLoading.value = value
    }

    protected fun launchSafety(
        errHandler: ((Throwable) -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit
    ) {
        _isLoading.value = true
        val errorHandler = CoroutineExceptionHandler { _, throwable ->
            errHandler?.invoke(throwable) ?: errHandler.apply {
                _evenState.value = Event.Toast(throwable.message.toString())
                _evenState.value = Event.Idle
                Log.e("Error!", throwable.message.toString())
            }
        }
        (viewModelScope + errorHandler).launch(Dispatchers.IO) {
            block()
        }.invokeOnCompletion { _isLoading.value = false }
    }
}