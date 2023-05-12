package com.example.profnotes.data.models

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "notes")
data class Notes (
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "date")
    var date: String?,

    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "description")
    val description: String
    ):Parcelable