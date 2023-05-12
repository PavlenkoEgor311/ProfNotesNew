package com.example.profnotes.core

import java.util.*

object Utils {
    fun generateUniqueId(): Long {
        val uuid = UUID.randomUUID()
        val id = uuid.mostSignificantBits xor uuid.leastSignificantBits
        return if (id < 0) id * (-1) else id
    }
}