package com.example.profnotes.mynote_rv

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profnotes.binding
import com.example.profnotes.databinding.ItemNoteStartBinding
import com.example.profnotes.databinding.ItemStatusBinding
import com.example.profnotes.model.Note
import com.example.profnotes.model.StatusNote
interface statusActionListener{
    fun clickStatus(statusNote: StatusNote)
}
class RVAdapterStatus(
    private val statusActionListener: statusActionListener)
    :RecyclerView.Adapter<RVAdapterStatus.MyViewHolder>(), View.OnClickListener {

    class MyViewHolder(val binding: ItemStatusBinding):RecyclerView.ViewHolder(binding.root)

    private var listStatus = emptyList<StatusNote>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapterStatus.MyViewHolder {
        val view = ItemStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        view.root.setOnClickListener(this)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listStatus.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val statusNote = listStatus[position]
        with(holder.binding){
            holder.itemView.tag = statusNote
            ibStatus.setImageResource(listStatus[position].idImg)
            tvStatus.text = listStatus[position].status
            when (listStatus[position].status.trim()){
                "Отложено" -> tvStatus.setTextColor(Color.RED)
                "Новое" -> tvStatus.setTextColor(Color.BLUE)
                "В работе" -> tvStatus.setTextColor(Color.GREEN)
                "Выполнено" -> tvStatus.setTextColor(Color.YELLOW)
            }
            tvDescriptionStatus.text = listStatus[position].descriptionStatus
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setStatus(lst:List<StatusNote>){
        listStatus = lst
        notifyDataSetChanged()
    }

    override fun onClick(v: View) {
        val status = v.tag as StatusNote
        statusActionListener.clickStatus(status)
    }
}