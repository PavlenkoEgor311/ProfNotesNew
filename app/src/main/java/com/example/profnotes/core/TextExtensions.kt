package ru.mrz.profnotes.core

import android.os.Build
import android.text.Editable
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import androidx.annotation.RequiresApi
import androidx.core.text.toSpannable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Month
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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

fun String.toFormattedDate(): String? {
    val year = this.substring(4).toInt()
    val month = this.substring(2, 4).toInt() - 1 // месяцы в классе Calendar начинаются с 0
    val day = this.substring(0, 2).toInt()
    return try {
        val calendar = Calendar.getInstance().apply {
            set(year, month, day)
        }
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
        dateFormat.format(calendar.time)
    } catch (err: ParseException) {
        null
    }
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun String.isValid() = this.isNotEmpty() && this.length > 4
