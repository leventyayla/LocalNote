package com.task.noteapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.databinding.RowNoteBinding
import com.task.noteapp.db.entities.Note
import com.task.noteapp.util.NoteDiffUtil
import com.task.noteapp.util.load
import java.text.SimpleDateFormat
import java.util.*

class NoteRecyclerViewAdapter(
    private var items: List<Note> = arrayListOf(),
    private val listener: ((Note) -> Unit)? = null
): RecyclerView.Adapter<NoteRecyclerViewAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: RowNoteBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener?.invoke(items[adapterPosition])
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
        val item = items[position]

        holder.binding.noteImage.apply {
            item.image?.let {
                visibility = View.VISIBLE
                load(it)
            } ?: kotlin.run {
                visibility = View.GONE
            }
        }

        holder.binding.noteTitle.text = item.title
        holder.binding.noteDesc.text = item.description

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

    override fun getItemCount(): Int = items.size

    fun setData(newList: List<Note>) {
        val diffUtil = NoteDiffUtil(items, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        items = newList
        diffResults.dispatchUpdatesTo(this)
    }
}