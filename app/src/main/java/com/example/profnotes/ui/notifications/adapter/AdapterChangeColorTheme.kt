package com.example.profnotes.ui.notifications.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profnotes.databinding.ItemColorBinding

interface ColorActionListener {
    fun changeColor(color: String)
}

class AdapterChangeColorTheme (
    private val actionListener: ColorActionListener)
    : RecyclerView.Adapter<AdapterChangeColorTheme.MyViewHolder>(), View.OnClickListener{

    private var lstColor = emptyList<Int>()

     inner class MyViewHolder(
        val binding: ItemColorBinding)
        :RecyclerView.ViewHolder(binding.root) {

            init {
                binding.tvColorItem.setOnClickListener {
                    actionListener.changeColor(lstColor[position].toString())
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterChangeColorTheme.MyViewHolder {
        val view = ItemColorBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterChangeColorTheme.MyViewHolder, position: Int) {
        val color = lstColor[position]
        with(holder){
            binding.tvColorItem.setImageResource(color)
            holder.itemView.tag = color
        }
    }

    override fun getItemCount(): Int {
        return lstColor.size
    }

    override fun onClick(v: View) {
        val color =  v.tag
        actionListener.changeColor(color = color.toString())
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<Int>) {
        lstColor = newItems
        notifyDataSetChanged()
    }

}