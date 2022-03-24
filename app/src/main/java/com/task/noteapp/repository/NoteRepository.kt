package com.task.noteapp.repository

import com.task.noteapp.db.entities.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun insertAll(vararg notes: Note)

    suspend fun update(note: Note)
}