package com.example.profnotes.ui.addNote

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.profnotes.R
import com.example.profnotes.data.models.Notes
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.databinding.FragmentAddNoteBinding
import com.example.profnotes.databinding.FragmentHomeBinding
import com.example.profnotes.model.NewNote
import com.example.profnotes.ui.addNote.adapter.AdapterVP2AddNote
import com.example.profnotes.viewmodel.AddLocalNoteViewModel
import com.example.profnotes.viewmodel.AddNoteViewModel
import com.example.profnotes.viewmodel.AddTaskViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddNoteFragment() : BaseFragment<FragmentAddNoteBinding,AddNoteViewModel>() {

    override val viewModel:AddNoteViewModel by viewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?) =
        FragmentAddNoteBinding.inflate(inflater, container, false)

    private val args by navArgs<AddNoteFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setVPandTab()
        binding.tabLayout.getTabAt(viewModel.getPosition())?.select()
    }

    override fun onStart() {
        super.onStart()
        mainActivity.showBottomBar(false)
        backToHome()
        if (arguments?.getString("MyArg").equals("addNewNote"))
            addNewNoteOrTask()
        else
            changeNoteOrTask()
    }

    private fun setInitValueLocalNote(){
        args.selectedNote.note?.let { viewModel.setId(it.id)
                                      viewModel.setTitle(it.title)
                                      viewModel.setDescription(it.description)
                                      viewModel.setDate(it.date) }
    }

    private fun setInitValueNewNote(){
        args.selectedNote.newNote?.let { viewModel.setIdOnlineNote(it.id)
                                         viewModel.setTitleOnlineNote(it.title)
                                         viewModel.setDescriptionOnlineNote(it.description)
                                         viewModel.setDateOnlineNote(it.date) }
    }

    private fun setInitValue(){
        if (args.selectedNote.newNote == null){
            setInitValueLocalNote()
            binding.vpAddNoteOrTask.setCurrentItem(1,false)
            binding.tabLayout.getTabAt(1)?.select()
        }
        else{
            setInitValueNewNote()
        }
    }

    private fun changeNoteOrTask() {
        setInitValue()
        with(binding){
            btAddNote.setOnClickListener{
            when(tabLayout.selectedTabPosition) {
                0 -> {
                    val newViewModel = getNewViewModelAddOnlineNote()
                    // Добавляем или изменям онлайн заметку
                }
                1 -> {
                    val newViewModel = getNewViewModelAddLocalNote()
                        if (newViewModel.getId() == 0) {
                            newViewModel.addNote(addNewValueLocalNote())
                            Toast.makeText(requireContext(),
                                            "Вы добавили заметку",
                                            Toast.LENGTH_SHORT).show()
                        }
                        else {
                            newViewModel.updateNote(addNewValueLocalNote())
                            Toast.makeText(requireContext(),
                                            "Вы изменили заметку",
                                            Toast.LENGTH_SHORT).show()
                        }
                        findNavController().navigate(R.id.action_addNoteFragment_to_navigation_home)
                    }
                }
            }
        }
    }

    private fun addNewNoteOrTask() {
        with(binding){
            btAddNote.setOnClickListener {
                when (tabLayout.selectedTabPosition) {
                    0 -> {
                            val newViewModel = getNewViewModelAddOnlineNote()
                            // Добавление онлайн заметки
                    }
                    1 -> {
                            val newViewModel = getNewViewModelAddLocalNote()
                            newViewModel.addNote(addNewValueLocalNote())
                            Toast.makeText(requireContext(),
                                        "Вы добавили новую заметку",
                                        Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_addNoteFragment_to_navigation_home)
                    }
                }
            }
        }
    }

    private fun getNewViewModelAddLocalNote(): AddNoteViewModel {
        return AddLocalNoteFragment(viewModel).getViewModel()
    }

    private fun getNewViewModelAddOnlineNote(): AddNoteViewModel {
        return AddTaskFragment(viewModel).returnViewModelWithData()
    }

    private fun addNewValueLocalNote():Notes{
        lateinit var note : Notes
        lifecycleScope.launch {
            viewModel.note.collect{
                if (it != null) {
                    note = Notes(it.id,it.title,it.date,"Новое",it.description)
                }
            }
        }.cancel()
        return note
    }

    private fun addNewValueOnlineNote(): NewNote{
        lateinit var note:NewNote
        lifecycleScope.launch {
            viewModel.newNote.collect{
                if (it != null) {
                    Toast.makeText( requireContext(),
                        "${it.title} заметка ${it.date} ${it.description}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }.cancel()
        return note
    }

    @SuppressLint("ResourceType")
    private fun setVPandTab() {
        val adapter = AdapterVP2AddNote(parentFragmentManager, lifecycle)
        adapter.addFragment(AddTaskFragment(viewModel), "Заметка")
        adapter.addFragment(AddLocalNoteFragment(viewModel), "Задача")
        with(binding){
            vpAddNoteOrTask.adapter = adapter
            vpAddNoteOrTask.isSaveFromParentEnabled=false
            vpAddNoteOrTask.setPageTransformer(
                MarginPageTransformer(30)
            )
            TabLayoutMediator(tabLayout, vpAddNoteOrTask) { tab, position ->
                tab.text = adapter.getPageTitle(position)
            }.attach()
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
            viewModel.setPosition(0)
            findNavController().navigate(R.id.action_addNoteFragment_to_navigation_home)
        }
    }
}
