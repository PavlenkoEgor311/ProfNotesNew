package com.example.profnotes.data.models

import android.os.Parcelable
import com.example.profnotes.viewmodel.core.Transformable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GlobalNote(
    val id: Long,
    val title: String,
    val description: String,
    val date: String,
    val status: String,
    val friendsId: MutableList<UserId>
) : Parcelable, Transformable<GlobalNoteNew> {

    override fun transform(vararg args: Any): GlobalNoteNew =
        GlobalNoteNew(
            id = id,
            idParent = args[0] as Long,
            title = title,
            description = description,
            status = status,
            date = date,
            friendsId = friendsId,
            files = mutableListOf()
        )

    fun transformRequest(idParent: Long) =
        GlobalNoteRequest(
            id = id,
            idParent = idParent,
            title = title,
            description = description,
            status = status,
            date = date,
            friendsId = friendsId.map { it.id }.toMutableList(),
            files = mutableListOf()
        )
}


@Parcelize
data class GlobalNoteRequest(
    val id: Long,
    val idParent: Long,
    val title: String,
    val description: String,
    val date: String,
    val status: String,
    val friendsId: MutableList<Long>,
    val files: MutableList<Int>
) : Parcelable