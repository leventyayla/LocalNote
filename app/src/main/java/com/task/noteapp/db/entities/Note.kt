package com.task.noteapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "image") var image: String? = null,
    @ColumnInfo(name = "created_date") var createdDate: Long = -1,
    @ColumnInfo(name = "edited_date") var editedDate: Long? = null
)