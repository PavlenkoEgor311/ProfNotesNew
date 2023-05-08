package com.example.profnotes.viewmodel.core

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

abstract class BaseViewModel : ViewModel(), CoroutineOperator {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _evenState = MutableStateFlow<Event>(Event.Idle)
    val evenState = _evenState.asStateFlow()

    private val loading = MutableLiveData(Loading.HIDE_LOADING)

    protected fun showLoading() {
        loading.value = Loading.SHOW_LOADING
    }

    protected fun hideLoading() {
        loading.value = Loading.HIDE_LOADING
    }

    fun observeLoading(owner: LifecycleOwner, onChanged: (newState: Loading) -> Unit) {
        loading.observe(owner, Observer { onChanged(it!!) })
    }

    override fun launchSafety(
        errHandler: ((Throwable) -> Unit)?,
        compHandler: (() -> Unit)?,
        loaderStart: (() -> Unit)?,
        loaderStop: (() -> Unit)?,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        val errHand = CoroutineExceptionHandler { _, err ->
            errHandler?.invoke(err) ?: errHandler.apply {
                _evenState.value = Event.Toast(err.message.toString())
                _evenState.value = Event.Idle
                Timber.tag("Error!").e(err.message.toString())
            }
        }

        val job: Job = (viewModelScope + errHand).launch {
            loaderStart?.invoke()
            block()
        }
        job.invokeOnCompletion {
            compHandler?.invoke()
            loaderStop?.invoke()
        }
        return job
    }
}

enum class Loading {
    SHOW_LOADING, HIDE_LOADING
}