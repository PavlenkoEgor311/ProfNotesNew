package com.example.profnotes.ui.errorScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.profnotes.databinding.FragmentErrorBinding
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.viewmodel.ErrorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ErrorFragment : BaseFragment<FragmentErrorBinding, ErrorViewModel>() {

    override val viewModel: ErrorViewModel by viewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentErrorBinding =
        FragmentErrorBinding.inflate(inflater, container, false)
}