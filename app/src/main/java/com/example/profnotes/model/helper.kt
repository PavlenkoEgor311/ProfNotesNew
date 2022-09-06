package com.example.profnotes.model

import android.os.Parcelable
import com.example.profnotes.data.models.Notes
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
@Parcelize
data class helper(
    val newNote:NewNote?,
    val note: Notes?
):Parcelable