package ru.mrz.profnotes.core

import android.os.Build
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import androidx.annotation.RequiresApi
import androidx.core.text.toSpannable
import java.time.DayOfWeek
import java.time.Month
import java.time.format.TextStyle
import java.util.*

fun CharSequence.spanString(startIndex: Int, endIndex: Int, color: Int): Spannable {
    val spannable = this.toSpannable()
    spannable.setSpan(
        ForegroundColorSpan(color),
        startIndex, endIndex,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannable
}

@RequiresApi(Build.VERSION_CODES.O)
fun Month.translate(): String {
    return this.getDisplayName(
        TextStyle.FULL,
        Locale("ru", "RU")
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun DayOfWeek.translate(): String {
    return this.getDisplayName(
        TextStyle.FULL,
        Locale("ru", "RU")
    )
}

fun Calendar.getNameMonth(): String? {
    return this.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("ru", "RU"))
}