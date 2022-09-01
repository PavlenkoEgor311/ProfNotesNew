package com.example.profnotes.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation.findNavController

import androidx.navigation.fragment.findNavController
import com.example.profnotes.R
import com.example.profnotes.data.models.Notes
import com.example.profnotes.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository): ViewModel() {

        fun getAllNotes(): Flow<List<Notes>> = flow {
            emit(authRepository.getNotes())
        }

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
}