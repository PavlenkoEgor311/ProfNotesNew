package com.example.profnotes.data.repo

import com.example.profnotes.data.network.Api.NotesApi
import com.example.profnotes.data.network.Api.RegisterApi
import com.example.profnotes.data.repo.core.BaseRepository
import com.example.profnotes.model.request.UserRequest
import javax.inject.Inject

open class NoteRepository @Inject constructor(
    private val notesApi: NotesApi,
    private val registerApi: RegisterApi
) : BaseRepository() {

}