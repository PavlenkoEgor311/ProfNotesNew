package com.example.profnotes.viewmodel

import androidx.lifecycle.*
import com.example.profnotes.data.models.Notes
import com.example.profnotes.data.repo.AuthRepository
import com.example.profnotes.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val authRepository: AuthRepository): BaseViewModel() {

    private var _note = MutableStateFlow<Notes?>(null)
    val note = _note.asStateFlow()

    private var _newNote = MutableStateFlow<Notes?>(null)
    val newNote = _newNote.asStateFlow()

    private var titleInput:String = ""
    private var dateInput:String = ""
    private var descriptionInput:String = ""

    var titleInputOnline:String = ""
    var dateInputOnline:String = ""
    var descriptionInputOnline:String = ""


    fun getTitle():String = titleInput
    fun getDate():String = dateInput
    fun getDescription():String = descriptionInput

    fun setTitle(title:String){
        titleInput = title
    }
    fun setDate(date:String){
        dateInput = date
    }
    fun setDescription(description:String){
        descriptionInput = description
    }
    fun setNote(note: Notes){
        _note.value = note
    }

    private var positionVp:Int = 0

    fun getPosition():Int = positionVp
    fun setPosition(newPosition:Int){
        positionVp = newPosition
    }

    fun addNote(note: Notes){
        viewModelScope.launch {
            authRepository.addNote(note)
        }
    }
    fun updateNote(note: Notes){
        viewModelScope.launch {
            authRepository.changeNote(note)
        }
    }
}