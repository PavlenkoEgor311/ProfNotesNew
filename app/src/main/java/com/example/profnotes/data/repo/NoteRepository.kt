package com.example.profnotes.data.repo

import com.example.profnotes.data.models.NoteNet
import com.example.profnotes.data.models.util.ResponseWrapper
import com.example.profnotes.data.network.Api.NotesApi
import com.example.profnotes.data.repo.core.BaseRepository
import com.example.profnotes.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

open class NoteRepository @Inject constructor(
    private val notesApi: NotesApi
) :BaseRepository(){
    suspend fun getNote() = getResponseInResponseWrapper(notesApi.getNote())
}