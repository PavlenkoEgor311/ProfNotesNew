package com.example.profnotes.viewmodel

import androidx.lifecycle.*
import com.example.profnotes.data.models.GlobalNote
import com.example.profnotes.data.models.Notes
import com.example.profnotes.data.repo.AuthRepository
import com.example.profnotes.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AddNoteViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private var _note = MutableStateFlow<Notes?>(null)
    val note = _note.asStateFlow()

//    private var _newNote = MutableStateFlow<NewNote?>(null)
//    val newNote = _newNote.asStateFlow()

    private var _globalNote = MutableStateFlow<GlobalNote?>(null)
    private val globalNote = _globalNote.asStateFlow()

    private var positionVp: Int = 0
    fun getPosition(): Int = positionVp
    fun setPosition(newPosition: Int) {
        positionVp = newPosition
    }

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

//    fun setNewNote(note: NewNote) {
//        _newNote.value = note
//    }


    fun setLocalNote(note: Notes) {
        _note.value = note
    }
    fun getLocalNote() = note.value

    fun setGlobalNote(note: GlobalNote) {
        _globalNote.value = note
    }
    fun getGlobalNote() = globalNote.value
}