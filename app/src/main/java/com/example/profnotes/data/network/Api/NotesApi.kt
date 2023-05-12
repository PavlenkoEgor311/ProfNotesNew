package com.example.profnotes.data.network.Api

import com.example.profnotes.data.models.GlobalNoteNew
import com.example.profnotes.data.models.GlobalNoteRequest
import retrofit2.Response
import retrofit2.http.*

interface NotesApi {

    @GET("getNotesUserById")
    suspend fun getNoteUser(@Query("idUser") id: Long): List<GlobalNoteNew>

    @POST("updateNote")
    suspend fun updateNote(
        @Body note: GlobalNoteNew
    ): Response<Body>

    @POST("insertNote")
    suspend fun insertNote(
        @Body note: GlobalNoteRequest
    )

    @DELETE
    suspend fun deleteNote(
        @Path("id") id: Long
    ): Boolean
}