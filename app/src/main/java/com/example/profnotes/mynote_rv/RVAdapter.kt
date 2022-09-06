package com.example.profnotes.mynote_rv

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profnotes.R
import com.example.profnotes.binding
import com.example.profnotes.data.models.Notes
import com.example.profnotes.databinding.FragmentLoginBinding
import com.example.profnotes.databinding.ItemNoteBinding
import com.example.profnotes.databinding.ItemNoteStartBinding
import com.example.profnotes.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteActionListener {
    fun deleteNote(note: Notes)
    fun changeNote(note: Notes)
    fun changestatusNote(note: Notes)
}

class RVAdapter(
    private val actionListener:NoteActionListener)
    :RecyclerView.Adapter<RVAdapter.MyViewHolder>(),View.OnClickListener{

    class MyViewHolder(val binding: ItemNoteStartBinding):RecyclerView.ViewHolder(binding.root)

    private var oldlistNote = emptyList<Notes>()

    // Создание и подгрузка нашей заметки
    // Так же обрабатыем нажатия на части разметки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.MyViewHolder {
        val view = ItemNoteStartBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        view.ibDelNote.setOnClickListener(this)
        view.ibChangeNote.setOnClickListener(this)
        view.note.tvChangeStatus.setOnClickListener(this)
        return MyViewHolder(view)
    }

    // Заполнение заметки
    override fun onBindViewHolder(holder: RVAdapter.MyViewHolder, position: Int) {
        val note = oldlistNote[position]
        with(holder){
            binding.ibDelNote.tag = note
            binding.ibChangeNote.tag = note
            binding.note.tvChangeStatus.tag = note
            binding.note.tvNoteStatus.text = oldlistNote[position].status
            when (oldlistNote[position].status.trim()){
                STATUS_COMPLETED_RED,STATUS_POSTEPONED -> {
                    binding.note.tvNoteStatus.setTextColor(Color.RED)
                    binding.note.lineStart.setBackgroundColor(Color.RED)
                }
                STATUS_NEW -> {
                    binding.note.tvNoteStatus.setTextColor(Color.BLUE)
                    binding.note.lineStart.setBackgroundColor(Color.BLUE)
                }
                STATUS_COMPLETED -> {
                    binding.note.tvNoteStatus.setTextColor(Color.YELLOW)
                    binding.note.lineStart.setBackgroundColor(Color.YELLOW)
                }
            }
            binding.note.tvNoteTitle.text = oldlistNote[position].title
            binding.note.tvNoteDescription.text = oldlistNote[position].description
            binding.note.tvChangeTime.text = oldlistNote[position].date
        }
    }

    override fun getItemCount(): Int {
        return oldlistNote.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setdataNote(list: List<Notes>){
        val diffUtil = DiffUtilNote(oldlistNote, list)
        val diffRes = DiffUtil.calculateDiff(diffUtil)
        this.oldlistNote = list as MutableList<Notes>
        diffRes.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    override fun onClick(v: View) {
        val note = v.tag as Notes
        when (v.id){
            R.id.ib_delNote ->{
                actionListener.deleteNote(note)
            }
            R.id.ib_changeNote->{
                 actionListener.changeNote(note)
            }
            R.id.tvChangeStatus->{
                actionListener.changestatusNote(note)
            }
        }

    }

    companion object{
        const val  STATUS_COMPLETED = "Выполнено"
        const val  STATUS_NEW = "Новое"
        const val  STATUS_COMPLETED_RED = "Завершено"
        const val  STATUS_POSTEPONED = "Отложено"
    }
}