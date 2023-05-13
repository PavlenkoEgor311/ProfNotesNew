package com.example.profnotes.data.repo

import com.example.profnotes.data.local.Prefs
import com.example.profnotes.data.models.GlobalNote
import com.example.profnotes.data.models.GlobalNoteNew
import com.example.profnotes.data.network.Api.NotesApi
import com.example.profnotes.data.repo.core.BaseRepository
import javax.inject.Inject

open class NoteRepository @Inject constructor(
    private val notesApi: NotesApi,
    private val prefs: Prefs
) : BaseRepository() {

    suspend fun getGlobalNote(id: Long): List<GlobalNoteNew> = notesApi.getNoteUser(id)

    suspend fun updateNote(note: GlobalNote) {
        notesApi.updateNote(note.transformRequest(prefs.idUser))
    }

    suspend fun insertNote(note: GlobalNote) {
        notesApi.insertNote(note.transformRequest(prefs.idUser))
    }

    suspend fun deleteNote(noteId: Long) {
        notesApi.deleteNote(noteId)
    }
}