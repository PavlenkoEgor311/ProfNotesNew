package com.example.profnotes.ui.addNote


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.profnotes.databinding.FragmentAddTaskBinding
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
}