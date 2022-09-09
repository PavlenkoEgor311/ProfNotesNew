package com.example.profnotes.ui.core

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.profnotes.MainActivity
import com.example.profnotes.viewmodel.core.BaseViewModel
import com.example.profnotes.viewmodel.core.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
abstract class BaseFragment<VB:ViewBinding,VM:BaseViewModel>() : Fragment() {

    protected abstract val viewModel:VM

    private var _binding: VB? = null
    val binding get() = _binding!!

    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    protected val mainActivity:MainActivity by lazy { requireActivity() as MainActivity }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?{
        _binding = inflateViewBinding(inflater, container)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            viewModel.isLoading.collect {
                //mainActivity.showLoading(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.evenState.onEach(::renderState)
        }
    }

    override fun onStart() {
        super.onStart()
        mainActivity.showBottomBar(true)
        mainActivity.setRoundNavBottom()
    }

    private fun renderState(event: Event) {
        when(event) {
            is Event.Idle -> {}
            is Event.Toast -> {
                Toast.makeText(requireContext(), event.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}