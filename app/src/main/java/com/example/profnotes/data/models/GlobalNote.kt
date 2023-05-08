package com.example.profnotes.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GlobalNote(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val friendId: List<Int>?
) : Parcelable