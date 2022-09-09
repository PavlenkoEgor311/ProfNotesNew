package com.example.profnotes.ui.addNote

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.profnotes.R
import com.example.profnotes.binding
import com.example.profnotes.core.invisible
import com.example.profnotes.core.visible
import com.example.profnotes.data.models.Notes
import com.example.profnotes.databinding.FragmentAddLocalNoteBinding
import com.example.profnotes.databinding.FragmentAddTaskBinding
import com.example.profnotes.databinding.FragmentChangeStatusBinding
import com.example.profnotes.model.NewNote
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.viewmodel.AddNoteViewModel
import com.example.profnotes.viewmodel.AddTaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment(
    private val vm:AddNoteViewModel
    ) : BaseFragment<FragmentAddTaskBinding, AddTaskViewModel>() {

    override val viewModel: AddTaskViewModel by viewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?) =
        FragmentAddTaskBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setETValue()
        binding.ibSearchFriends.setOnClickListener{
            binding.etSearchFriends.visible()
            binding.ibSearchFriends.invisible()
        }
    }

    private fun setETValue(){
        with(binding){
            etTitleNote.setText(vm.getTitleNewNote())
            etDescriptionNote.setText(vm.getDescriptionNewNote())
        }
    }

    private fun getNewValue() {
        with(binding) {
            etTitleNote.doAfterTextChanged {
                vm.setTitleOnlineNote(etTitleNote.text.toString())
                vm.setNewNote(
                    NewNote(
                        vm.getIdNewNote(),
                        title = etTitleNote.text.toString(),
                        date = vm.getDateNewNote().toString(),
                        description = etDescriptionNote.text.toString()
                    )
                )
            }
            etDescriptionNote.doAfterTextChanged {
                vm.setDateOnlineNote(etDescriptionNote.text.toString())
                vm.setNewNote(
                    NewNote(
                        vm.getIdNewNote(),
                        title = etTitleNote.text.toString(),
                        date = vm.getDateNewNote().toString(),
                        description = etDescriptionNote.text.toString()
                    )
                )
            }
            cvDataNote?.setOnDateChangeListener { _, year, month, dayOfMonth ->
                vm.setDateOnlineNote(
                    dayOfMonth.toString() + month.toString() + year.toString()
                )
            }
        }
    }

    fun returnViewModelWithData(): AddNoteViewModel {
        return vm
    }

    override fun onStart() {
        super.onStart()
        mainActivity.showBottomBar(false)
        getNewValue()
    }

}