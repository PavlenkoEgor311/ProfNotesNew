package com.example.profnotes.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation.findNavController

import androidx.navigation.fragment.findNavController
import com.example.profnotes.R
import com.example.profnotes.data.models.Notes
import com.example.profnotes.data.repo.AuthRepository
import com.example.profnotes.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.NullPointerException
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
}