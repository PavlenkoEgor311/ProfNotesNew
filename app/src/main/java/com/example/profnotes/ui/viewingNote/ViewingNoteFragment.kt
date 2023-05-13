package com.example.profnotes.ui.viewingNote

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.profnotes.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.findNavController
import com.example.profnotes.databinding.FragmentViewingNoteBinding
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.viewmodel.ViewingNoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.navArgs
import com.example.profnotes.core.styleText.Typo
import com.example.profnotes.data.models.GlobalNoteNew
import com.example.profnotes.data.models.Notes
import com.example.profnotes.data.models.UserFindRequest
import com.example.profnotes.model.TransitNote
import com.example.profnotes.ui.addNote.screen.AddLocalScreen
import com.example.profnotes.ui.addNote.screen.AddNetScreen
import com.example.profnotes.ui.viewingNote.screen.ShowNote
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ViewingNoteFragment : BaseFragment<FragmentViewingNoteBinding, ViewingNoteViewModel>() {

    override val viewModel: ViewingNoteViewModel by viewModels()
    private val args by navArgs<ViewingNoteFragmentArgs>()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentViewingNoteBinding.inflate(inflater, container, false)

    private val globalNote: GlobalNoteNew?
        get() = viewModel.getGlobalNote()

    private val localNote: Notes?
        get() = viewModel.getLocalNote()

    private lateinit var listSelectedFriends: List<UserFindRequest>
    private lateinit var checkArgs: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArgs = checkArgs()
        lifecycleScope.launch {
            viewModel.usersFriends.collect {
                listSelectedFriends = it
            }
        }
        hideBottomNavigation()
        setupLoading()
        binding.cvContent.setContent {
            Content(checkArgs)
        }
    }

    private fun checkArgs(): String {
        return when (val myArgs = args.myNote) {
            is TransitNote.Local -> {
                viewModel.setLocalNote(myArgs.note!!)
                myArgs.key
            }
            is TransitNote.Global -> {
                viewModel.setGlobalNote(myArgs.note!!)
                viewModel.getSelectedFriends()
                myArgs.key
            }
        }
    }

    @Composable
    private fun Content(keyAction: String) {
        var visibleEdit by remember { mutableStateOf(true) }
        val navController = rememberNavController()
        val key = viewModel.getValueFactoryKey(keyAction)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Black)
        ) {
            Toolbar(visibleEdit, navController)
            NavHost(
                navController = navController,
                startDestination = if (keyAction == keyLocalNoteShow || keyAction == keyGlobalNoteShow) {
                    "showNote"
                } else {
                    "editNote"
                },
            ) {
                composable("showNote") {
                    if (globalNote != null) {
                        ShowNote(
                            id = globalNote!!.id,
                            title = globalNote!!.title,
                            description = globalNote!!.description,
                            date = globalNote!!.date,
                            listSelectedFriend = listSelectedFriends,
                            key = key,
                            listAddedFiles = listOf(),
                        )
                    } else {
                        if (localNote != null)
                            ShowNote(
                                id = localNote!!.id.toLong(),
                                title = localNote!!.title,
                                description = localNote!!.description,
                                date = localNote!!.date,
                                listSelectedFriend = listOf(),
                                key = key,
                                listAddedFiles = listOf(),
                            )
                    }
                    visibleEdit = true
                }
                composable("editNote") {
                    OpenChangeNoteScreen()
                    visibleEdit = false
                }
            }
        }
    }

    @Composable
    private fun Toolbar(visibleEdit: Boolean, navController: NavHostController) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_new_24),
                contentDescription = null,
                modifier = Modifier
                    .background(Transparent)
                    .clickable {
                        if (navController.previousBackStackEntry?.destination?.route == "showNote") {
                            navController.popBackStack()
                        } else {
                            findNavController().popBackStack()
                        }
                    }
            )

            Button(
                modifier = Modifier,
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    if (navController.currentBackStackEntry?.destination?.route == "editNote") {
                        changeLocalOrGlobalNote()
                    } else {
                        navController.navigate("editNote")
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Yellow)
            ) {
                Text(
                    text = if (visibleEdit)
                        stringResource(id = R.string.edit_note)
                    else
                        stringResource(id = R.string.save),
                    color = Black,
                    style = Typo.DefaultTypography.h3
                )
            }
        }
    }

    private fun changeLocalOrGlobalNote() {
        viewModel.changeNote()
        findNavController().popBackStack()
    }

    @Composable
    private fun OpenChangeNoteScreen() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (globalNote != null) {
                    AddNetScreen(
                        globalNote = globalNote!!.transform(0L),
                        returnNote = {
                            viewModel.setGlobalNote(it.transform(0L))
                        },
                        friends = listSelectedFriends
                    )
                } else {
                    AddLocalScreen(
                        localNote = localNote,
                        returnLocalNote = {
                            viewModel.setLocalNote(it)
                        })
                }
            }
        }
    }

    companion object {
        const val keyLocalNoteShow = "localShow"
        const val keyGlobalNoteShow = "globalShow"
        const val keyLocalNoteEdit = "localEdit"
    }
}