package com.example.profnotes.note_for_viewpager

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profnotes.databinding.NotevpBinding
import com.example.profnotes.model.NewNote
import java.util.Collections.emptyList

class NoteVPAdapter : RecyclerView.Adapter<NoteVPAdapter.ViewHolder>() {
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
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(NotevpBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<NewNote>) {
        items = newItems
        notifyDataSetChanged()
    }
}