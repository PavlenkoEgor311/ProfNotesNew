package com.example.profnotes.note_for_viewpager

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profnotes.databinding.ItemStatusBinding
import com.example.profnotes.databinding.NotevpBinding
import com.example.profnotes.model.NewNote
import com.example.profnotes.model.StatusNote
import com.example.profnotes.mynote_rv.NoteActionListener
import java.util.Collections.emptyList

interface noteVPActionListener{
    fun clickNote(newNote: NewNote)
}

class NoteVPAdapter(
    private val noteActionListener: noteVPActionListener):
    RecyclerView.Adapter<NoteVPAdapter.ViewHolder>(), View.OnClickListener {
    private var items = emptyList<NewNote>()
    class ViewHolder(private val binding: NotevpBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewNote){
            with(binding) {
                tvTitle.text = item.title
                tvDescription.text = item.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = NotevpBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        view.root.setOnClickListener(this)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.tag = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<NewNote>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onClick(v: View) {
        val note = v.tag as NewNote
        noteActionListener.clickNote(note)
    }
}