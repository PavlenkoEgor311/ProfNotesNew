package com.example.profnotes.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.profnotes.data.local.Prefs
import com.example.profnotes.data.models.GlobalNoteNew
import com.example.profnotes.data.models.Notes
import com.example.profnotes.data.models.User
import com.example.profnotes.data.repo.AuthRepository
import com.example.profnotes.data.repo.NoteRepository
import com.example.profnotes.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val noteRepository: NoteRepository,
    private val prefs: Prefs,
) : BaseViewModel() {

    private val _userProfile = MutableStateFlow<User?>(null)
    val userProfile = _userProfile.asStateFlow()

    private val _globalNotes = MutableStateFlow<List<GlobalNoteNew>>(listOf())
    val globalNotes = _globalNotes.asStateFlow()

    fun getAllNotes(): Flow<List<Notes>> = flow {
        emit(authRepository.getNotes())
    }

    fun getLstForViewPager(): List<GlobalNoteNew> {
        launchSafety {
            showLoading()
            _globalNotes.value = noteRepository.getGlobalNote(prefs.idUser)
            hideLoading()
        }
        return globalNotes.value
    }


    fun addNote(note: Notes) {
        viewModelScope.launch {
            authRepository.addNote(note)
        }
    }

    fun delNote(note: Notes) {
        viewModelScope.launch {
            authRepository.delNote(note)
        }
    }

    fun searchInLocalNotes(searchString: String): Flow<List<Notes>> = flow {
        emit(authRepository.getSearchLocalNote(searchString))
    }

    fun getData() {
        launchSafety {
            showLoading()
            _userProfile.value = authRepository.getData()
            hideLoading()
        }
    }
}