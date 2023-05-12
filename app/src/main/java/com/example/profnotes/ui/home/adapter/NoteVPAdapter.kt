package com.example.profnotes.note_for_viewpager

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profnotes.core.gone
import com.example.profnotes.data.models.GlobalNoteNew
import com.example.profnotes.databinding.NotevpBinding
import java.util.Collections.emptyList

class NoteVPAdapter(
    private val onClick: (note: GlobalNoteNew) -> Unit
) :
    RecyclerView.Adapter<NoteVPAdapter.ViewHolder>() {
    private var items = emptyList<GlobalNoteNew>()

    class ViewHolder(private val binding: NotevpBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GlobalNoteNew) {
            with(binding) {
                tvTitle.text = item.title
                tvStatus.text = "Статус задачи : ${item.status}"
                tvDescription.text = item.description
                tvDateNote.text = item.date

                if (item.friendsId.isEmpty()) {
                    tvCountFriend.gone()
                } else {
                    tvCountFriend.text = "Вы и ваших ${item.friendsId.size} друзей"
                }
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
    fun setItems(newItems: List<GlobalNoteNew>) {
        items = newItems
        notifyDataSetChanged()
    }
}