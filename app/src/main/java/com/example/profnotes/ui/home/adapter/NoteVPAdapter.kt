package com.example.profnotes.note_for_viewpager

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profnotes.data.models.GlobalNote
import com.example.profnotes.databinding.NotevpBinding
import java.util.Collections.emptyList


class NoteVPAdapter(
    val onClick: (note: GlobalNote) -> Unit
) :
    RecyclerView.Adapter<NoteVPAdapter.ViewHolder>() {
    private var items = emptyList<GlobalNote>()

    class ViewHolder(private val binding: NotevpBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GlobalNote) {
            with(binding) {
                tvTitle.text = item.title
                tvDescription.text = item.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = NotevpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<GlobalNote>) {
        items = newItems
        notifyDataSetChanged()
    }
}