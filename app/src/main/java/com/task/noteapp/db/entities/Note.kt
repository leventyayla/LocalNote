package com.task.noteapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image") val image: String? = null,
    @ColumnInfo(name = "created_date") val createdDate: Long,
    @ColumnInfo(name = "edited_date") val editedDate: Long? = null
)