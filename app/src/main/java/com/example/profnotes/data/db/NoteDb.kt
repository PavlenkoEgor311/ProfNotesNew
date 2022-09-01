package com.example.profnotes.data.db

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.example.profnotes.data.dao.NotesDao
import com.example.profnotes.data.models.Notes


@Database (entities = [Notes::class],version = 1, exportSchema = false)

abstract class NoteDb : RoomDatabase() {
    abstract fun noteDao() : NotesDao

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Note-Room"
    }
}