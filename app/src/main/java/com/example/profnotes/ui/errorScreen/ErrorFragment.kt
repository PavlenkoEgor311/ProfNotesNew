package com.example.profnotes.ui.errorScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.profnotes.databinding.FragmentErrorBinding
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.viewmodel.ErrorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ErrorFragment : BaseFragment<FragmentErrorBinding, ErrorViewModel>() {

    override val viewModel: ErrorViewModel by viewModels()
    private val args by navArgs<ErrorFragmentArgs>()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentErrorBinding =
        FragmentErrorBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitleError.text = args.error
        binding.ivBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        hideBottomNavigation()
    }
}