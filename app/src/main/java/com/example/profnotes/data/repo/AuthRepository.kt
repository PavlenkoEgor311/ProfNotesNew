package com.example.profnotes.data.repo

import com.example.profnotes.R
import com.example.profnotes.data.dao.NotesDao
import com.example.profnotes.data.local.Prefs
import com.example.profnotes.data.models.GlobalNote
import com.example.profnotes.data.models.Notes
import com.example.profnotes.data.models.SignInUser
import com.example.profnotes.data.network.Api.RegisterApi
import com.example.profnotes.model.StatusNote
import com.example.profnotes.model.request.LoginResponse
import com.example.profnotes.model.request.UserRequest
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val prefs: Prefs,
    private val notesDao: NotesDao,
    private val registerApi: RegisterApi,
) {

    private val exStatusNote: List<StatusNote> = listOf(
        StatusNote(R.mipmap.status_new, "Новое", "Только что созданная задача"),
        StatusNote(R.mipmap.status_work, "В работе", "Активный этап выполнения"),
        StatusNote(R.mipmap.status_performed, "Выполнено", "Задача полностью выполнена"),
        StatusNote(R.mipmap.status_postponed, "Отложено", "Задача требует задаржки в работе")
    )

    private val lstForViewPager = listOf(
        GlobalNote(1, "Выполнение дз к понедельнику", "17:00", "Новое", listOf()),
        GlobalNote(2, "Выполнение дз к вторнику", "11:00", "Завершено", listOf()),
        GlobalNote(2, "Выполнение дз к chtlt", "11:00", "Завершено", listOf())
    )

    private val lstColorsTheme = listOf(
        R.color.blue,
        R.color.red,
        R.color.green,
        R.color.darkGray,
        R.color.lightGray,
        R.color.yellow,
        R.color.purple_700
    )

    fun getColorList(): List<Int> = lstColorsTheme

    fun getLstForViewPager(): List<GlobalNote> = lstForViewPager

    fun getAllStatus(): List<StatusNote> = exStatusNote

    fun getIsFirstEnter() = prefs.isFirstEnter

    fun setIsFirstEnter(value: Boolean) {
        prefs.isFirstEnter = value
    }

    fun getIsUserAuth() = prefs.authUser

    suspend fun addNote(note: Notes) {
        notesDao.addNote(note)
    }

    suspend fun delNote(note: Notes) {
        notesDao.deleteNote(note)
    }

    suspend fun changeNote(note: Notes) {
        notesDao.updateNote(note)
    }

    suspend fun checkNoteInDb(note: Notes) {
        notesDao.getNoteById(note.id)
    }

    suspend fun getNotes(): List<Notes> = notesDao.getAllNotes()

    suspend fun getSearchLocalNote(search: String): List<Notes> = notesDao.searchInDb(search)

    fun setIsUserAuth(value: Boolean) {
        prefs.authUser = value
    }

    fun setUserToken(value: String) {
        prefs.tokenUser = value
    }
    fun setUserID(value: Long) {
        prefs.idUser = value
    }

    fun getUserId(): Long = prefs.idUser
    fun getUserToken(): String? = prefs.tokenUser

    suspend fun test() = registerApi.test()
    suspend fun signIn(user: SignInUser) {
        registerApi.signIn(user)
    }
    suspend fun logIn(user: UserRequest): LoginResponse = registerApi.logIn(user)
}