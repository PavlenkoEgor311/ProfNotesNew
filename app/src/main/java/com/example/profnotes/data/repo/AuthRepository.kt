package com.example.profnotes.data.repo

import com.example.profnotes.R
import com.example.profnotes.data.dao.NotesDao
import com.example.profnotes.data.local.Prefs
import com.example.profnotes.data.models.Notes
import com.example.profnotes.model.NewNote
import com.example.profnotes.model.StatusNote
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val prefs: Prefs,
    private val notesDao: NotesDao) {

    private val exStatusNote :List<StatusNote> = listOf(
        StatusNote(R.drawable.ic_test_user,"Новое","Только что созданная задача"),
        StatusNote(R.drawable.ic_test_user, "В работе","Активный этап выполнения"),
        StatusNote(R.drawable.ic_test_user,"Выполнено","Задача полностью выполнена"),
        StatusNote(R.drawable.ic_test_user, "Отложено","Задача требует задаржки в работе")
    )

    private val lstForViewPager = listOf( NewNote(1,"Выполнение дз к понедельнику", "17:00","Новое"),
                                          NewNote(2,"Выполнение дз к вторнику", "11:00","Завершено"),
                                          NewNote(2,"Выполнение дз к chtlt", "11:00","Завершено") )

    fun getLstForViewPager():List<NewNote> = lstForViewPager

    fun getAllStatus(): List<StatusNote> = exStatusNote

    fun getIsFirstEnter() = prefs.isFirstEnter

    fun setIsFirstEnter(value: Boolean){
        prefs.isFirstEnter = value
    }

    suspend fun addNote(note: Notes){
        notesDao.addNote(note)
    }

    suspend fun delNote(note: Notes){
        notesDao.deleteNote(note)
    }

    suspend fun changeNote(note: Notes){
        notesDao.updateNote(note)
    }

    suspend fun checkNoteInDb(note: Notes){
        notesDao.getNoteById(note.id)
    }

    suspend fun getNotes():List<Notes> = notesDao.getAllNotes()

    fun getSearchLocalNote(search:String):List<Notes> = notesDao.searchInDb(search)
}