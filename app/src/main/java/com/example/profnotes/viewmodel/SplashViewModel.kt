package com.example.profnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profnotes.data.models.NoteNet
import com.example.profnotes.data.models.Notes
import com.example.profnotes.data.models.util.ResponseWrapper
import com.example.profnotes.data.repo.AuthRepository
import com.example.profnotes.data.repo.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository:AuthRepository,
    private val noteRepository: NoteRepository) : ViewModel(){

    private var _note  = MutableStateFlow<ResponseWrapper<NoteNet>>(ResponseWrapper.Idle())
    val note = _note.asStateFlow()

    fun getIsFirstEnter() = authRepository.getIsFirstEnter()

    fun setIsFirstEnter(value: Boolean){
        authRepository.setIsFirstEnter(value)
    }

    fun addNote(note: Notes){
            viewModelScope.launch {
                authRepository.addNote(note)
            }
    }
    fun getNote(){
        viewModelScope.launch {
            _note.value = noteRepository.getNote()
        }
    }
}