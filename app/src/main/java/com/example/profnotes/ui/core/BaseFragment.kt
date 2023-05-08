package com.example.profnotes.ui.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.profnotes.MainActivity
import com.example.profnotes.core.base.activity.BottomBarOperator
import com.example.profnotes.data.dao.NotesDao
import com.example.profnotes.viewmodel.core.BaseViewModel
import com.example.profnotes.viewmodel.core.Event
import com.example.profnotes.viewmodel.core.Loading
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    protected abstract val viewModel: VM
    private var _binding: VB? = null
    val binding get() = _binding!!

    @Inject
    lateinit var dao: NotesDao

    private val root
        get() = requireActivity()

    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    protected val mainActivity: MainActivity by lazy { requireActivity() as MainActivity }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
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

    private fun renderState(event: Event) {
        when (event) {
            is Event.Idle -> {}
            is Event.Toast -> {
                Toast.makeText(requireContext(), event.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    protected fun showBottomNavigation() =
        (root as? BottomBarOperator)?.showNavBar()

    protected fun hideBottomNavigation() =
        (root as? BottomBarOperator)?.hideNavBar()

    protected fun hideCircularProgress() =
        (root as? BottomBarOperator)?.hideCircularProgress()

    protected fun showCircularProgress() =
        (root as? BottomBarOperator)?.showCircularProgress()

    protected fun setupLoading() {
        viewModel.observeLoading(owner = viewLifecycleOwner) {
            when (it) {
                Loading.HIDE_LOADING -> {
                    hideCircularProgress()
                }
                else -> {
                    showCircularProgress()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}