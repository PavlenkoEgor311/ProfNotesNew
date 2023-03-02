package com.example.profnotes.data.models

data class GlobalNote(
    val id: Int,
    val title: String?,
    val description: String?,
    val date: String?,
    val friendId: List<Int>?
)