package com.example.profnotes.data.models

import android.os.Parcelable
import com.example.profnotes.viewmodel.core.Transformable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GlobalNoteNew(
    val id: Long,
    val idParent: Long,
    val title: String,
    val description: String,
    val date: String,
    val status: String,
    val friendsId: MutableList<UserId>,
    val files: MutableList<FileId>
) : Transformable<GlobalNote>, Parcelable {
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
