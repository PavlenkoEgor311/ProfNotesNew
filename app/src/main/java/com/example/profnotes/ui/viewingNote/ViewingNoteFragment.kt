package com.example.profnotes.ui.viewingNote

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.profnotes.R
import com.example.profnotes.viewmodel.ViewingNoteViewModel

class ViewingNoteFragment : Fragment() {

    companion object {
        fun newInstance() = ViewingNoteFragment()
    }

    private lateinit var viewModel: ViewingNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_viewing_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewingNoteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}