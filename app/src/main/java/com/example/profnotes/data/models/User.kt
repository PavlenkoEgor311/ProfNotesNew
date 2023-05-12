package com.example.profnotes.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class User(
    val id: Long,
    val username: String,
    val login: String,
    val friendsId: List<UserId>
)

data class UpdateUserRequest(
    val id: Long,
    val username: String?,
    val password: String?,
)

data class UserFriendId(
    val userId: Long,
)

@Parcelize
data class UserId(
    val id: Long
) : Parcelable

@Parcelize
data class FileId(
    val id: Long
) : Parcelable

data class UpdateUserFriendsRequest(
    val idUser: Long,
    val idFriend: Long,
)
