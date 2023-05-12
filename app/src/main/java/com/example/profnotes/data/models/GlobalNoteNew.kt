package com.example.profnotes.data.models

import com.example.profnotes.viewmodel.core.Transformable

data class GlobalNoteNew(
    val id: Long,
    val idParent: Long,
    val title: String,
    val description: String,
    val date: String,
    val status: String,
    val friendsId: MutableList<UserId>,
    val files: MutableList<FileId>
) : Transformable<GlobalNote> {
    override fun transform(vararg args: Any): GlobalNote =
        GlobalNote(
            id = id,
            title = title,
            description = description,
            date = date,
            status = status,
            friendsId = friendsId,
        )
}
