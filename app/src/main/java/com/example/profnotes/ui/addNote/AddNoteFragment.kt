package com.example.profnotes.ui.addNote

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.profnotes.R
import com.example.profnotes.core.BaseFragment
import com.example.profnotes.databinding.FragmentAddNoteBinding
import com.example.profnotes.ui.addNote.adapter.AdapterVP2AddNote
import com.example.profnotes.viewmodel.AddNoteViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment() : BaseFragment<AddNoteViewModel>() {

    override val viewModel:AddNoteViewModel by activityViewModels()

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<AddNoteFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setVP()
        if (arguments?.getString("MyArg").equals("addNewNote"))
            addNewNoteOrTask()
        else
            changeNoteOrTask()
        backToHome()
    }

    private fun changeNoteOrTask() {
        with(binding){
            btAddNote.setOnClickListener{
                Toast.makeText(requireContext(),"${tabLayout.selectedTabPosition}",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addNewNoteOrTask() {
        with(binding){
            btAddNote.setOnClickListener{
//                lifecycleScope.launchWhenStarted {
//                    viewModel.note.collect{
//                        Toast.makeText(requireContext(), "$it заметка",Toast.LENGTH_SHORT).show()
//                    }
//                }
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun setVP() {
        val adapter = AdapterVP2AddNote(parentFragmentManager, lifecycle)
        adapter.addFragment(AddTaskFragment(), "timetable)")
        adapter.addFragment(AddLocalNoteFragment(), "homework)")
        with(binding){
            vpAddNoteOrTask.adapter = adapter
            vpAddNoteOrTask.setPageTransformer(
                MarginPageTransformer(30)
            )
            TabLayoutMediator(tabLayout, vpAddNoteOrTask) { tab, position ->
                tab.text = adapter.getPageTitle(position)
            }.attach()
        }
        addNewNoteOrTask()
    }
    private fun backToHome(){
        binding.btBackToHome.setOnClickListener{
            findNavController().navigate(R.id.action_addNoteFragment_to_navigation_home)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
