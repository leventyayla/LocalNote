package com.task.noteapp.repository

import com.task.noteapp.db.daos.NoteDao
import com.task.noteapp.db.entities.Note
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class NoteLocalDataSource @Inject constructor(private val noteDao: NoteDao) : NoteRepository {

    override fun getAllNotes() = flow {
        noteDao.getAll()
            .debounce(100)
            .distinctUntilChanged()
            .collect {
                emit(it)
            }
    }

    override suspend fun insertAll(vararg notes: Note) {
        noteDao.insertAll(*notes)
    }

    override suspend fun update(note: Note) {
        noteDao.update(note)
    }
}