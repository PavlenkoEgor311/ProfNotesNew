package com.example.profnotes.data.dao
import androidx.room.*
import com.example.profnotes.data.models.Notes
import java.util.concurrent.Flow

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Notes)

    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<Notes>

    @Update
    suspend fun updateNote(note:Notes)

    @Delete
    suspend fun deleteNote(note:Notes)

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Int): Notes

    @Query("SELECT * FROM NOTES WHERE title LIKE :search OR description LIKE :search OR date LIKE :search")
    fun searchInDb(search:String) : List<Notes>
}