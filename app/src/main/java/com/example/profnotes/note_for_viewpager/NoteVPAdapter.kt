package com.example.profnotes.note_for_viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.profnotes.ui.home.HomeFragment

class NoteVPAdapter(fragment: HomeFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 10

    override fun createFragment(position: Int): Fragment {
        val fragment = NoteVPFragment()
        fragment.arguments = Bundle().apply {
            putInt("", position + 1)
        }
        return fragment
    }
}