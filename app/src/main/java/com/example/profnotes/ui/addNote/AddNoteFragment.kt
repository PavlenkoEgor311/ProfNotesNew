package com.example.profnotes.ui.addNote

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.profnotes.data.models.GlobalNote
import com.example.profnotes.data.models.Notes
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.databinding.FragmentAddNoteBinding
import com.example.profnotes.ui.addNote.screen.AddLocalScreen
import com.example.profnotes.ui.addNote.screen.AddNetScreen
import com.example.profnotes.viewmodel.AddNoteViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding, AddNoteViewModel>() {

    override val viewModel: AddNoteViewModel by viewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddNoteBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentPositionTab = viewModel.getPosition()
        binding.tabLayout.getTabAt(currentPositionTab)?.select()
        setIniScreen(currentPositionTab)
        setScreen()
    }

    private fun setIniScreen(position: Int) {
        with(binding) {
            when (position) {
                0 ->
                    lifecycleScope.launchWhenCreated {
                        cvContent.setContent {
                            AddNetScreen(globalNote = null, returnNote = {})
                        }
                    }
                1 -> lifecycleScope.launchWhenCreated {
                    cvContent.setContent {
                        AddLocalScreen(localNote = null, returnLocalNote = {})
                    }
                }
                else -> TODO()
            }
        }
    }


    override fun onStart() {
        super.onStart()
        mainActivity.showBottomBar(false)
        yankieGoHome()
    }

    private fun setScreen() {
        with(binding) {
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when (tab.position) {
                        0 -> {
                            viewModel.setPosition(0)
                            lifecycleScope.launch {
                                binding.cvContent.setContent {
                                    AddNetScreen(
                                        globalNote = viewModel.getGlobalNote(),
                                        returnNote = {
                                            viewModel.setGlobalNote(
                                                GlobalNote(
                                                    0, it.title, it.description,
                                                    it.date,
                                                    it.friendId
                                                )
                                            )
                                        })
                                }
                            }
                            btAddNote.setOnClickListener {
                                Toast.makeText(
                                    requireContext(),
                                    "Добавили Глобальную заметку",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        1 -> {
                            viewModel.setPosition(1)
                            lifecycleScope.launch {
                                binding.cvContent.setContent {
                                    AddLocalScreen(
                                        returnLocalNote = {
                                            viewModel.setLocalNote(
                                                Notes(
                                                    0,
                                                    it.title,
                                                    it.date,
                                                    it.status,
                                                    it.description
                                                )
                                            )
                                        }, localNote = viewModel.getLocalNote()
                                    )
                                }
                            }
                            btAddNote.setOnClickListener {
                                viewModel.addNote(viewModel.getLocalNote()!!)
                                Toast.makeText(
                                    requireContext(),
                                    "Добавили локальную заметку",
                                    Toast.LENGTH_SHORT
                                ).show()
                                findNavController().popBackStack()
                            }

                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun yankieGoHome() {
        binding.btBackToHome.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
