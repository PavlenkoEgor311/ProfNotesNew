package com.example.profnotes.viewmodel

import com.example.profnotes.data.models.Notes
import com.example.profnotes.model.NewNote
import com.example.profnotes.ui.viewingNote.ViewingNoteFragment.Companion.keyLocalNoteEdit
import com.example.profnotes.ui.viewingNote.ViewingNoteFragment.Companion.keyLocalNoteShow
import com.example.profnotes.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ViewingNoteViewModel @Inject constructor() : BaseViewModel() {

    private var _localNote = MutableStateFlow<Notes?>(null)
    private var localNote = _localNote.asStateFlow()
    fun setLocalNote(note: Notes) {
        _localNote.value = note
    }

    fun getLocalNote() = localNote.value

    private var _globalNote = MutableStateFlow<NewNote?>(null)
    private var globalNote = _globalNote.asStateFlow()
    fun setGlobalNote(note: NewNote) {
        _globalNote.value = note
    }

    fun getGlobalNote() = globalNote.value

    fun getValueFactoryKey(key: String): Boolean =
        when (key) {
            keyLocalNoteShow -> false
            keyLocalNoteEdit -> false
            else -> true
        }
}