package com.example.profnotes.model

import android.os.Parcelable
import com.example.profnotes.data.models.GlobalNote
import com.example.profnotes.data.models.Notes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransitionNote(
    val note: Notes?,
    val newNote: GlobalNote?,
    val key: String,
) : Parcelable

@Parcelize
sealed class TransitNote : Parcelable {
    data class Local(val note: Notes?, val key: String) : TransitNote()
    data class Global(val note: GlobalNote?, val key: String) : TransitNote()
}