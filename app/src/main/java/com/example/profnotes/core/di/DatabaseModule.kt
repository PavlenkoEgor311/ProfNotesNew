package com.example.profnotes.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.transition.Visibility
import com.example.profnotes.data.dao.NotesDao
import com.example.profnotes.data.db.NoteDb
import com.example.profnotes.data.local.Prefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun providePrefs(@ApplicationContext context: Context):Prefs = Prefs(context)

    @Provides
    @Singleton
    fun provideDao(db: NoteDb):NotesDao = db.noteDao()

    @Provides
    @Singleton
    fun provideDB(application: Application) :NoteDb =
        Room.databaseBuilder(application,NoteDb::class.java,NoteDb.DATABASE_NAME).build()
}