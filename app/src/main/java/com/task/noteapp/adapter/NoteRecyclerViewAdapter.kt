package com.task.noteapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.databinding.RowNoteBinding
import com.task.noteapp.db.entities.Note
import com.task.noteapp.util.load
import java.text.SimpleDateFormat
import java.util.*

class NoteRecyclerViewAdapter(
    private val listener: ((Note) -> Unit)? = null
): RecyclerView.Adapter<NoteRecyclerViewAdapter.NoteViewHolder>() {

    private val diffUtil = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
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
    })

    inner class NoteViewHolder(val binding: RowNoteBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener?.invoke(diffUtil.currentList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = RowNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = diffUtil.currentList[position]

        holder.binding.noteImage.apply {
            item.image?.let {
                visibility = View.VISIBLE
                load(it)
            } ?: kotlin.run {
                visibility = View.GONE
            }
        }

        takeIf { item.title.isNotEmpty() }?.run {
            holder.binding.noteTitle.visibility = View.VISIBLE
            holder.binding.noteTitle.text = item.title
        } ?: kotlin.run {
            holder.binding.noteTitle.visibility = View.GONE
        }

        takeIf { item.description.isNotEmpty() }?.run {
            holder.binding.noteDesc.visibility = View.VISIBLE
            holder.binding.noteDesc.text = item.description
        } ?: kotlin.run {
            holder.binding.noteDesc.visibility = View.GONE
        }

        val date: Long = item.editedDate?.let {
            setEdited(holder.binding.noteEdited, true)
            it
        } ?: kotlin.run {
            setEdited(holder.binding.noteEdited, false)
            item.createdDate
        }
        holder.binding.noteDate.text = convertLongToTime(date)
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return format.format(date)
    }

    private fun setEdited(view: View, edited: Boolean) {
        view.visibility = if (edited) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int = diffUtil.currentList.size

    fun setData(newList: List<Note>) {
        diffUtil.submitList(newList)
    }
}