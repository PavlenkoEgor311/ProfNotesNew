package com.example.profnotes.core

import androidx.fragment.app.Fragment
import com.example.profnotes.MainActivity
import com.example.profnotes.viewmodel.BaseViewModel

abstract class BaseFragment<T :BaseViewModel> : Fragment() {
    protected abstract val viewModel:T
    protected val mainActivity: MainActivity get() = requireActivity() as MainActivity
}