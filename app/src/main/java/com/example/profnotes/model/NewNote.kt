package com.example.profnotes.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewNote(
    val id: Int,
    val title: String,
    val date: String,
    val description: String
): Parcelable