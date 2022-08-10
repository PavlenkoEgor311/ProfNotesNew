package com.example.profnotes.mynote_rv

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profnotes.databinding.FragmentLoginBinding
import com.example.profnotes.databinding.ItemNoteBinding
import com.example.profnotes.databinding.ItemNoteStartBinding
import com.example.profnotes.model.Note

class RVAdapter:RecyclerView.Adapter<RVAdapter.MyViewHolder>(){
    class MyViewHolder(val binding: ItemNoteStartBinding):RecyclerView.ViewHolder(binding.root)

    private var oldlistNote = emptyList<Note>()

    // Создание и подгрузка нашей заметки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.MyViewHolder {
        val view = ItemNoteStartBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)
    }

    // Заполнение заметки
    override fun onBindViewHolder(holder: RVAdapter.MyViewHolder, position: Int) {
        holder.binding.note.tvNoteStatus.text = oldlistNote[position].status
        when (oldlistNote[position].status.trim()){
            "Завершено","Отложено" -> { holder.binding.note.tvNoteStatus.setTextColor(Color.RED)
                                        holder.binding.note.lineStart.setBackgroundColor(Color.RED)
                                        }
            "Новое" -> {holder.binding.note.tvNoteStatus.setTextColor(Color.BLUE)
                        holder.binding.note.lineStart.setBackgroundColor(Color.BLUE)}
        }
        holder.binding.note.tvNoteTitle.text = oldlistNote[position].title
        holder.binding.note.tvNoteDescription.text = oldlistNote[position].description
        holder.binding.note.tvChangeTime.text = oldlistNote[position].date
    }

    override fun getItemCount(): Int {
        return oldlistNote.size
    }

    fun setdataNote(list:List<Note>){
        val diffUtil = DiffUtilNote(oldlistNote, list)
        val diffRes = DiffUtil.calculateDiff(diffUtil)
        oldlistNote = list
        diffRes.dispatchUpdatesTo(this)
    }
}