package com.task.noteapp.db.daos

import androidx.room.*
import com.task.noteapp.db.entities.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY created_date DESC")
    fun getAll(): Flow<List<Note>>

    @Insert
    suspend fun insertAll(vararg notes: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}