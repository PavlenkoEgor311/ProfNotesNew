package com.example.profnotes.ui.addNote

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.profnotes.R
import com.example.profnotes.binding
import com.example.profnotes.core.invisible
import com.example.profnotes.core.visible
import com.example.profnotes.data.models.Notes
import com.example.profnotes.databinding.FragmentAddLocalNoteBinding
import com.example.profnotes.databinding.FragmentAddTaskBinding
import com.example.profnotes.databinding.FragmentChangeStatusBinding
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.viewmodel.AddNoteViewModel


class AddTaskFragment : BaseFragment<FragmentAddTaskBinding, AddNoteViewModel>() {

    override val viewModel: AddNoteViewModel by activityViewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?) =
        FragmentAddTaskBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ibSearchFriends.setOnClickListener{
            binding.etSearchFriends.visible()
            binding.ibSearchFriends.invisible()
        }
    }

    private fun getNewValue(){
        with(binding){
//            etTitleNote.addTextChangedListener(object : TextWatcher {
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//                }
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    viewModel.setTitle(etTitleNote.text.toString())
//                    viewModel.setNote(Notes(0, title = etTitleNote.text.toString(), date = etDateNote.text.toString(), status = "Новое", description = etDescriptionNote.text.toString()))
//                }
//                override fun afterTextChanged(s: Editable?) {
//                    viewModel.setTitle(etTitleNote.text.toString())
//                    viewModel.setNote(Notes(0, title = etTitleNote.text.toString(), date = etDateNote.text.toString(), status = "Новое", description = etDescriptionNote.text.toString()))
//                }
//            })
//            etDescriptionNote.addTextChangedListener(object : TextWatcher {
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//                }
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    viewModel.setDescription(etDescriptionNote.text.toString())
//                    viewModel.setNote(Notes(0, title = etTitleNote.text.toString(), date = etDateNote.text.toString(), status = "Новое", description = etDescriptionNote.text.toString()))
//                }
//                override fun afterTextChanged(s: Editable?) {
//                    viewModel.setDescription(etDescriptionNote.text.toString())
//                    viewModel.setNote(Notes(0, title = etTitleNote.text.toString(), date = etDateNote.text.toString(), status = "Новое", description = etDescriptionNote.text.toString()))
//                }
//            })
            ////Calendar

        }
    }

    override fun onStart() {
        super.onStart()

    }

}