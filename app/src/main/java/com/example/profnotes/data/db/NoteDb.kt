package com.example.profnotes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.profnotes.data.dao.NotesDao
import com.example.profnotes.data.db.NoteDb.Companion.DATABASE_VERSION
import com.example.profnotes.data.models.Notes

@Database (entities = [Notes::class],version = DATABASE_VERSION, exportSchema = false)

abstract class NoteDb : RoomDatabase() {
    abstract fun noteDao() : NotesDao

    companion object {
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "Note-Room"
    }
}