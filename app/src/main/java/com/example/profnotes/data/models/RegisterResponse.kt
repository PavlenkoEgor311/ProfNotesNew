package com.example.profnotes.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class FindUserRequest(
    val userId: Long,
    val username: String,
)

@Parcelize
data class UserFindRequest(
    val id: Long,
    val username: String,
) : Parcelable