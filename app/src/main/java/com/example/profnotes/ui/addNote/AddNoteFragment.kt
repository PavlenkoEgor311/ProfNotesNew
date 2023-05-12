package com.example.profnotes.ui.addNote

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.findNavController
import com.example.profnotes.core.Utils
import com.example.profnotes.data.models.GlobalNote
import com.example.profnotes.data.models.Notes
import com.example.profnotes.data.models.UserFindRequest
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.databinding.FragmentAddNoteBinding
import com.example.profnotes.ui.addNote.screen.AddLocalScreen
import com.example.profnotes.ui.addNote.screen.AddNetScreen
import com.example.profnotes.viewmodel.AddNoteViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.mrz.profnotes.core.toFormattedDate
import timber.log.Timber

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
        setupLoading()
        lifecycleScope.launch {
            viewModel.getData()
            viewModel.usersFriends.collect {
                showCircularProgress()
                hideBottomNavigation()
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0))
                yankieGoHome()
                binding.cvContent.setContent {
                    Content(it)
                }
            }
        }
    }

    private fun yankieGoHome() {
        binding.btBackToHome.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @Composable
    private fun Content(userFindRequests: List<UserFindRequest>) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "addGlobalNote",
        ) {
            composable("addGlobalNote") {
                AddNetScreen(
                    friends = userFindRequests,
                    globalNote = viewModel.getGlobalNote(),
                    returnNote = {
                        viewModel.setGlobalNote(
                            GlobalNote(
                                Utils.generateUniqueId(),
                                it.title,
                                it.description,
                                it.date,
                                it.status,
                                it.friendsId
                            )
                        )
                    })
            }
            composable("addLocalScreen") {
                AddLocalScreen(localNote = viewModel.getLocalNote(), returnLocalNote = {
                    viewModel.setLocalNote(
                        Notes(
                            0,
                            it.title,
                            it.date,
                            it.status,
                            it.description
                        )
                    )
                })
            }
        }
        SetAllScreen(navController = navController)
    }

    @Composable
    private fun SetAllScreen(navController: NavHostController) {
        with(binding) {
            if (tabLayout.selectedTabPosition == 0) {
                btAddNote.setOnClickListener {
//                    Toast.makeText(
//                        requireContext(),
//                        "${viewModel.getGlobalNote()}",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    Timber.e(viewModel.getGlobalNote().toString())
                    viewModel.insertNewGlobalNote()
                    findNavController().popBackStack()
                }
            }
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when (tab.position) {
                        0 -> {
                            navController.navigate("addGlobalNote")
                            btAddNote.setOnClickListener {
                                Toast.makeText(
                                    requireContext(),
                                    "${viewModel.getGlobalNote()}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                findNavController().popBackStack()
                            }
                        }
                        1 -> {
                            navController.navigate("addLocalScreen")
                            btAddNote.setOnClickListener {
                                if (viewModel.getLocalNote()?.date?.toFormattedDate() == null) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Введите корректную дату",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    viewModel.addNote(viewModel.getLocalNote()!!)
                                    findNavController().popBackStack()
                                }
                            }
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
    }


    override fun onStart() {
        super.onStart()
        hideCircularProgress()
    }
}
