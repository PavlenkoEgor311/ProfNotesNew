package com.example.profnotes.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.profnotes.data.models.Notes
import com.example.profnotes.model.Note

class DiffUtilNote(
    private val oldlstNotes:List<Notes>,
    private val newlstNotes: List<Notes>):DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldlstNotes.size

    override fun getNewListSize(): Int = newlstNotes.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlstNotes[oldItemPosition].id == newlstNotes[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlstNotes[oldItemPosition].id == newlstNotes[newItemPosition].id &&
                oldlstNotes[oldItemPosition].title == newlstNotes[newItemPosition].title &&
                oldlstNotes[oldItemPosition].date == newlstNotes[newItemPosition].date &&
                oldlstNotes[oldItemPosition].status == newlstNotes[newItemPosition].status &&
                oldlstNotes[oldItemPosition].description == newlstNotes[newItemPosition].description
    }
}