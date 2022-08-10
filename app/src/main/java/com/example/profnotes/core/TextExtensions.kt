package ru.mrz.profnotes.core

import android.text.Spannable
import android.text.style.ForegroundColorSpan
import androidx.core.text.toSpannable

fun CharSequence.spanString(startIndex: Int, endIndex: Int, color: Int): Spannable {
    val spannable = this.toSpannable()
    spannable.setSpan(
        ForegroundColorSpan(color),
        startIndex, endIndex,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannable
}