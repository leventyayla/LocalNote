package com.task.noteapp.di

import android.content.Context
import androidx.room.Room
import com.task.noteapp.db.NoteDatabase
import com.task.noteapp.db.daos.NoteDao
import com.task.noteapp.repository.NoteLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NoteModule {

    companion object {
        const val DB_NAME = "NoteDatabase"
    }

    @Provides
    @Singleton
    fun provideNoteRepositoryImpl(noteDao: NoteDao) = NoteLocalDataSource(noteDao)

    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.noteDao()
    }

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext appContext: Context): NoteDatabase {
        return Room.databaseBuilder(
            appContext,
            NoteDatabase::class.java,
            DB_NAME
        ).build()
    }
}