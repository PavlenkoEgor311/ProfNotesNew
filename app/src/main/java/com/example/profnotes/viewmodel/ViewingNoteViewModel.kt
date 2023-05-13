package com.example.profnotes.viewmodel


import androidx.lifecycle.viewModelScope
import com.example.profnotes.data.models.*
import com.example.profnotes.data.repo.AuthRepository
import com.example.profnotes.data.repo.NoteRepository
import com.example.profnotes.ui.viewingNote.ViewingNoteFragment.Companion.keyLocalNoteEdit
import com.example.profnotes.ui.viewingNote.ViewingNoteFragment.Companion.keyLocalNoteShow
import com.example.profnotes.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewingNoteViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val noteRepository: NoteRepository,
) : BaseViewModel() {

    private var _localNote = MutableStateFlow<Notes?>(null)
    var localNote = _localNote.asStateFlow()
    fun setLocalNote(note: Notes) {
        _localNote.value = note
    }

    fun getLocalNote() = localNote.value

    private var _globalNote = MutableStateFlow<GlobalNoteNew?>(null)
    var globalNote = _globalNote.asStateFlow()

    fun setGlobalNote(note: GlobalNoteNew) {
        _globalNote.value = note
    }

    fun getGlobalNote() = _globalNote.value

    fun getValueFactoryKey(key: String): Boolean =
        when (key) {
            keyLocalNoteShow -> false
            keyLocalNoteEdit -> false
            else -> true
        }

    private val _usersFriends = MutableStateFlow<List<UserFindRequest>>(listOf())
    val usersFriends = _usersFriends.asStateFlow()

    fun getSelectedFriends() {
        launchSafety {
            showLoading()
            _usersFriends.value =
                authRepository.getListFriends(_globalNote.value!!.friendsId) ?: listOf()
            hideLoading()
        }
    }

    fun changeNote() {
        if (_localNote.value != null) {
            viewModelScope.launch {
                authRepository.changeNote(_localNote.value!!)
            }
        } else {
            launchSafety {
                showLoading()
                runCatching {
                    noteRepository.updateNote(_globalNote.value!!.transform())
                }.onFailure {
                    Timber.e(it.toString())
                }
                hideLoading()
            }
        }
    }

}