package com.example.profnotes.ui.home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.profnotes.R
import com.example.profnotes.data.models.Notes
import com.example.profnotes.databinding.FragmentHomeBinding
import com.example.profnotes.model.NewNote
import com.example.profnotes.model.helper
import com.example.profnotes.mynote_rv.NoteActionListener
import com.example.profnotes.mynote_rv.RVAdapter
import com.example.profnotes.note_for_viewpager.NoteVPAdapter
import com.example.profnotes.note_for_viewpager.noteVPActionListener
import com.example.profnotes.ui.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false)

    private lateinit var adapter : NoteVPAdapter
    private lateinit var adapterRV: RVAdapter
    private lateinit var rvAllNotes : RecyclerView

    override val viewModel:HomeViewModel by viewModels()


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewPager()
        setRecyclerView()
    }

    private fun setViewPager(){
        adapter = NoteVPAdapter(object : noteVPActionListener{
            override fun clickNote(newNote: NewNote) {
                val elem = helper(newNote,null)
                val action = HomeFragmentDirections.actionNavigationHomeToAddNoteFragment(elem)
                findNavController().navigate(action)
            }
        })
        binding.vpNewnotes.setPageTransformer(
            MarginPageTransformer(20)
        )
        adapter.setItems(viewModel.getLstForViewPager())
        binding.vpNewnotes.adapter = adapter
    }

    private fun setElemToRV(){
        lifecycleScope.launch {
            viewModel.getAllNotes().collectLatest {
                adapterRV.setdataNote(it)
            }
        }
    }

    private fun setRecyclerView(){
        adapterRV = RVAdapter(object: NoteActionListener{
            override fun deleteNote(note: Notes){
                viewModel.delNote(note)
                Toast.makeText(requireContext(),"Заметка успешно удалена", Toast.LENGTH_SHORT).show()
                setElemToRV()
            }
            override fun changeNote(note: Notes) {
                val elem = helper(null,note)
                val action = HomeFragmentDirections.actionNavigationHomeToAddNoteFragment(elem)
                findNavController().navigate(action)
            }
            override fun changestatusNote(note: Notes) {
                val action = HomeFragmentDirections.actionNavigationHomeToChangeStatusFragment(note)
                findNavController().navigate(action)
            }
        })
        rvAllNotes = binding.rvAllnotes
        rvAllNotes.layoutManager = LinearLayoutManager(requireContext())
        rvAllNotes.adapter = adapterRV
        setElemToRV()
        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_addNoteFragment,)
        }
    }

    override fun onStart() {
        super.onStart()
    }
}