package com.example.profnotes.core

import java.util.*

object Utils {
    fun generateUniqueId(): Long {
        val uuid = UUID.randomUUID()
        val id = uuid.mostSignificantBits xor uuid.leastSignificantBits
        return if (id < 0) id * (-1) else id
    }

    fun getGreeting(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 6..11 -> "Доброе утро"
            in 12..17 -> "Добрый день"
            in 18..23, in 0..5 -> "Добрый вечер"
            else -> "Привет"
        }
    }
}