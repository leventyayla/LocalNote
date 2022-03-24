package com.task.noteapp.repository

import com.task.noteapp.db.daos.NoteDao
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
}