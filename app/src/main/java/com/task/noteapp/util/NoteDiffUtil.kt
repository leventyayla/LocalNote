package com.task.noteapp.util

import androidx.recyclerview.widget.DiffUtil
import com.task.noteapp.db.entities.Note

class NoteDiffUtil(
    private val oldList: List<Note>,
    private val newList: List<Note>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return when {
            oldItem.id != newItem.id -> false
            oldItem.title != newItem.title -> false
            oldItem.description != newItem.description -> false
            oldItem.image != newItem.image -> false
            oldItem.createdDate != newItem.createdDate -> false
            oldItem.editedDate != newItem.editedDate -> false
            else -> true
        }
    }
}