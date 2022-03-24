package com.task.noteapp.viewmodel

import androidx.lifecycle.*
import com.task.noteapp.db.entities.Note
import com.task.noteapp.repository.NoteLocalDataSource
import com.task.noteapp.util.FabStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val localDataSource: NoteLocalDataSource
) : ViewModel() {

    var note: Note? = null
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
}