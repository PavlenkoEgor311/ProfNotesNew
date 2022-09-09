package com.example.profnotes.ui.addNote

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.data.models.Notes
import com.example.profnotes.databinding.FragmentAddLocalNoteBinding
import com.example.profnotes.databinding.FragmentAddNoteBinding
import com.example.profnotes.viewmodel.AddLocalNoteViewModel
import com.example.profnotes.viewmodel.AddNoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddLocalNoteFragment(
    private val vm:AddNoteViewModel)
    : BaseFragment<FragmentAddLocalNoteBinding,AddLocalNoteViewModel>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?) =
        FragmentAddLocalNoteBinding.inflate(inflater, container, false)

    override val viewModel: AddLocalNoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setETValue()
    }

    private fun setETValue(){
        with(binding){
            etDateNote.setText(vm.getDate())
            etTitleNote.setText(vm.getTitle())
            etDescriptionNote.setText(vm.getDescription())
        }
    }

    private fun getNewValue(){
        with(binding){
            etTitleNote.doAfterTextChanged {
                vm.setTitle(etTitleNote.text.toString())
                vm.setNote(Notes(vm.getId(),
                                        title = etTitleNote.text.toString(),
                                        date = etDateNote.text.toString(),
                                        status = "Новое",
                                        description = etDescriptionNote.text.toString()))
            }
            etDescriptionNote.doAfterTextChanged {
                vm.setDescription(etDescriptionNote.text.toString())
                vm.setNote(Notes(vm.getId(),
                                        title = etTitleNote.text.toString(),
                                        date = etDateNote.text.toString(),
                                        status = "Новое",
                                        description = etDescriptionNote.text.toString()))
            }
            etDateNote.doAfterTextChanged {
                vm.setDate(etDateNote.text.toString())
                vm.setNote(Notes(vm.getId(),
                                        title = etTitleNote.text.toString(),
                                        date = etDateNote.text.toString(),
                                        status = "Новое",
                                        description = etDescriptionNote.text.toString()))
            }
        }
    }

    fun getViewModel(): AddNoteViewModel {
        return vm
    }

    override fun onStart() {
        super.onStart()
        mainActivity.showBottomBar(false)
        getNewValue()
    }
}
