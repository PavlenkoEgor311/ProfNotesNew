package com.example.profnotes.data.network.Api

import com.example.profnotes.data.models.NoteNet
import com.example.profnotes.data.models.NoteResponse
import com.example.profnotes.model.Note
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NotesApi {
    @GET("notes")
    fun getNotes(): NoteResponse

    @GET("todos/1")
    suspend fun getNote() : Response<NoteNet>
}