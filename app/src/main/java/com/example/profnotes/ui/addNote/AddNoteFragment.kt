package com.example.profnotes.ui.addNote

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.profnotes.R
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.databinding.FragmentAddNoteBinding
import com.example.profnotes.databinding.FragmentHomeBinding
import com.example.profnotes.ui.addNote.adapter.AdapterVP2AddNote
import com.example.profnotes.viewmodel.AddNoteViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddNoteFragment() : BaseFragment<FragmentAddNoteBinding,AddNoteViewModel>() {

    override val viewModel:AddNoteViewModel by activityViewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?) =
        FragmentAddNoteBinding.inflate(inflater, container, false)

    private val args by navArgs<AddNoteFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setVPandTab()
    }

    override fun onStart() {
        super.onStart()
        backToHome()
        if (arguments?.getString("MyArg").equals("addNewNote"))
            addNewNoteOrTask()
        else
            changeNoteOrTask()
    }

    private fun changeNoteOrTask() {
        with(binding){
            when(tabLayout.selectedTabPosition){
                0->{
                    // Меняем новую онлайн заметку
                }
                1->{
                    //// меняем новую заметку
                    btAddNote.setOnClickListener{
                        Toast.makeText(requireContext(),"${tabLayout.selectedTabPosition}",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun addNewNoteOrTask() {
        with(binding){
            btAddNote.setOnClickListener{

            }
        }
    }
    private fun addNewValueLocalNote(){
        lifecycleScope.launch {
            viewModel.note.collect{
                if (it != null) {
                    Toast.makeText( requireContext(),
                        "${it.title} заметка ${it.date} ${it.description}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }.cancel()
    }

    private fun addNewValueOnlineNote(){
        lifecycleScope.launch {
            viewModel.newNote.collect{
                if (it != null) {
                    Toast.makeText( requireContext(),
                        "${it.title} заметка ${it.date} ${it.description}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }.cancel()
    }

    @SuppressLint("ResourceType")
    private fun setVPandTab() {
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
            vpAddNoteOrTask.currentItem = viewModel.getPosition()
            tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when(tab.position){
                        0 -> viewModel.setPosition(0)
                        1 -> viewModel.setPosition(1)
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }
    }

    private fun backToHome(){
        binding.btBackToHome.setOnClickListener{
            findNavController().navigate(R.id.action_addNoteFragment_to_navigation_home)
        }
    }
}
