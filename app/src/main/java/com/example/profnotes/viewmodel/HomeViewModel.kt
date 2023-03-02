package com.example.profnotes.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.profnotes.data.models.Notes
import com.example.profnotes.data.repo.AuthRepository
import com.example.profnotes.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository): BaseViewModel() {

        fun getAllNotes(): Flow<List<Notes>> = flow {
            emit(authRepository.getNotes())
        }

        fun getLstForViewPager() = authRepository.getLstForViewPager()

        fun addNote(note: Notes){
            viewModelScope.launch {
                authRepository.addNote(note)
            }
        }

        fun delNote(note: Notes) {
            viewModelScope.launch {
                authRepository.delNote(note)
            }
        }

        fun searchInLocalNotes(searchString:String) : Flow<List<Notes>> = flow {
            emit(authRepository.getSearchLocalNote(searchString))
        }
}