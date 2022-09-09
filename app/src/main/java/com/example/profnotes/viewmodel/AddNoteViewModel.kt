package com.example.profnotes.viewmodel

import androidx.lifecycle.*
import com.example.profnotes.data.models.Notes
import com.example.profnotes.data.repo.AuthRepository
import com.example.profnotes.model.NewNote
import com.example.profnotes.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AddNoteViewModel @Inject constructor(
    private val authRepository: AuthRepository): BaseViewModel() {

    private var _note = MutableStateFlow<Notes?>(null)
    val note = _note.asStateFlow()

    private var _newNote = MutableStateFlow<NewNote?>(null)
    val newNote = _newNote.asStateFlow()

    private var idNote:Int = 0
    private var titleInput:String? = null
    private var dateInput:String? = null
    private var descriptionInput:String? = null

    private var idNewNote:Int = 0
    private var titleInputOnline:String? = null
    private var dateInputOnline:String? = null
    private var descriptionInputOnline:String? = null

    fun getId():Int = idNote
    fun getTitle(): String? = titleInput
    fun getDate():String? = dateInput
    fun getDescription():String? = descriptionInput

    fun getIdNewNote():Int = idNewNote
    fun getTitleNewNote(): String? = titleInputOnline
    fun getDateNewNote():String? = dateInputOnline
    fun getDescriptionNewNote():String? = descriptionInputOnline

    fun setId(id:Int){
        idNote = id
    }
    fun setTitle(title:String?){
        titleInput = title
    }
    fun setDate(date:String?){
        dateInput = date
    }
    fun setDescription(description:String?){
        descriptionInput = description
    }

    fun setIdOnlineNote(id:Int){
        idNewNote = id
    }
    fun setTitleOnlineNote(title:String?){
        titleInputOnline = title
    }
    fun setDateOnlineNote(date:String?){
        dateInputOnline = date
    }
    fun setDescriptionOnlineNote(description:String?){
        dateInputOnline = description
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

    fun setNote(note: Notes){
        _note.value = note
    }
    fun setNewNote(note:NewNote){
        _newNote.value = note
    }
}