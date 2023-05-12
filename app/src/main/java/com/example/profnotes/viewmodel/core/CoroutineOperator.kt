package com.example.profnotes.viewmodel.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface CoroutineOperator {
    fun launchSafety(
        errHandler: ((Throwable) -> Unit)? = null,
        compHandler: (() -> Unit)? = null,
        loaderStart: (() -> Unit)? = null,
        loaderStop: (() -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit
    ): Job
}

interface Transformable<T> {
    fun transform(vararg args: Any = emptyArray()): T
}