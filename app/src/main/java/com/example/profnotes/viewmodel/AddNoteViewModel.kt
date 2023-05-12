package com.example.profnotes.viewmodel

import androidx.lifecycle.*
import com.example.profnotes.data.models.GlobalNote
import com.example.profnotes.data.models.Notes
import com.example.profnotes.data.models.User
import com.example.profnotes.data.models.UserFindRequest
import com.example.profnotes.data.repo.AuthRepository
import com.example.profnotes.data.repo.NoteRepository
import com.example.profnotes.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AddNoteViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val noteRepository: NoteRepository,
) : BaseViewModel() {

    private val _userProfile = MutableStateFlow<User?>(null)
    val userProfile = _userProfile.asStateFlow()
    private val _usersFriends = MutableStateFlow<List<UserFindRequest>>(listOf())
    val usersFriends = _usersFriends.asStateFlow()
    private var _note = MutableStateFlow<Notes?>(null)
    val note = _note.asStateFlow()

    private var _globalNote = MutableStateFlow<GlobalNote?>(null)
    private val globalNote = _globalNote.asStateFlow()

    private var positionVp: Int = 0

    fun addNote(note: Notes) {
        viewModelScope.launch {
            authRepository.addNote(note)
        }
    }

    fun updateNote(note: Notes) {
        viewModelScope.launch {
            authRepository.changeNote(note)
        }
    }

    fun setLocalNote(note: Notes) {
        _note.value = note
    }

    fun getLocalNote() = note.value
    fun setGlobalNote(note: GlobalNote) {
        _globalNote.value = note
    }

    fun getGlobalNote() = globalNote.value

    fun getData() {
        launchSafety {
            showLoading()
            _userProfile.value = authRepository.getData()
            if (userProfile.value != null && userProfile.value!!.friendsId.isNotEmpty()) {
                _usersFriends.value =
                    authRepository.getListFriends(userProfile.value!!.friendsId) ?: listOf()
            }
            hideLoading()
        }
    }

    fun insertNewGlobalNote() {
        if (globalNote.value != null) {
            launchSafety {
                showLoading()
                noteRepository.insertNote(globalNote.value!!)
                hideLoading()
            }
        }
    }
}