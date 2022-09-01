package com.example.profnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profnotes.data.models.Notes
import com.example.profnotes.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeStatusViewModel @Inject constructor(
    private val authRepository: AuthRepository): ViewModel() {

    private var selectedStatus: String = ""
    private var statusButtom:Boolean = false

    fun getStatusButton():Boolean = statusButtom

    fun setStatusButton(newStatus:Boolean){
        statusButtom = newStatus
    }

    fun setSelectedStatus(status:String){
        selectedStatus = status
    }

    fun getSelectedStatus(): String = selectedStatus

    fun getAllStatusEX() = authRepository.getAllStatus()

    fun updateStatusNote(note: Notes){
        viewModelScope.launch {
            authRepository.changeNote(note)
        }
    }
}