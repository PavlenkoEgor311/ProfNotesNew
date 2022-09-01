package com.example.profnotes.viewmodel

import android.provider.ContactsContract
import androidx.lifecycle.*
import com.example.profnotes.data.models.NoteNet
import com.example.profnotes.data.models.Notes
import com.example.profnotes.data.models.util.ResponseWrapper
import com.example.profnotes.data.models.util.noteAndTaskWrapper
import com.example.profnotes.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val authRepository: AuthRepository):BaseViewModel() {

    private var _note = MutableStateFlow<String?>("Old")
    val note = _note.asStateFlow()

    val newNote:MutableLiveData<Notes> by lazy {
        MutableLiveData<Notes>()
    }

    fun addNote(note: Notes){
        viewModelScope.launch {
            authRepository.addNote(note)
        }
    }
    fun setNote(sk:String){
        _note.value = sk
    }
}