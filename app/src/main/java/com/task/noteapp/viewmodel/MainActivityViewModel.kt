package com.task.noteapp.viewmodel

import androidx.lifecycle.*
import com.task.noteapp.db.entities.Note
import com.task.noteapp.repository.NoteLocalDataSource
import com.task.noteapp.util.FabStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val localDataSource: NoteLocalDataSource
) : ViewModel() {

    lateinit var note: Note
    val fabStyle = MutableLiveData(FabStyle.CREATE)

    fun getAllNotes(): LiveData<List<Note>> {
        return localDataSource.getAllNotes()
            .asLiveData(context = viewModelScope.coroutineContext)
    }

    fun getFabStyle(): FabStyle {
        return fabStyle.value!!
    }

    fun setFabStyle(style: FabStyle) {
        fabStyle.value = style
    }

    fun saveOrUpdate() = viewModelScope.launch {
        if (note.title.isEmpty() && note.description.isEmpty()) return@launch
        takeIf { note.createdDate == -1L }?.let {
            note.createdDate = System.currentTimeMillis()
            localDataSource.insertAll(note)
        } ?: kotlin.run {
            note.editedDate = System.currentTimeMillis()
            localDataSource.update(note)
        }
    }
}