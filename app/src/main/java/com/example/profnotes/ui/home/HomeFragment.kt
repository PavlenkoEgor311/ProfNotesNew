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
import com.example.profnotes.MainActivity
import com.example.profnotes.R
import com.example.profnotes.data.models.Notes
import com.example.profnotes.databinding.FragmentHomeBinding
import com.example.profnotes.model.NewNote
import com.example.profnotes.model.Note
import com.example.profnotes.mynote_rv.NoteActionListener
import com.example.profnotes.mynote_rv.RVAdapter
import com.example.profnotes.note_for_viewpager.NoteVPAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { NoteVPAdapter() }

    private lateinit var adapterRV: RVAdapter
    private lateinit var rvAllNotes : RecyclerView

    private val viewModel:HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewPager()
        setRecyclerView()
    }

    private fun setViewPager(){
        binding.vpNewnotes.setPageTransformer(
            MarginPageTransformer(20)
        )
        val Nnote1 = NewNote(1,"Выполнение дз к понедельнику", "17:00","Новое")
        val Nnote2 = NewNote(2,"Выполнение дз к вторнику", "11:00","Завершено")
        val Nnote3 = NewNote(2,"Выполнение дз к вторнику", "11:00","Завершено")
        adapter.setItems(listOf(Nnote1,Nnote2,Nnote3))
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
                val action = HomeFragmentDirections.actionNavigationHomeToAddNoteFragment(note)
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
            findNavController().navigate(R.id.action_navigation_home_to_changeStatusFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}