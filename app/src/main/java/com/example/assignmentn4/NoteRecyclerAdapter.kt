package com.example.assignmentn4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteRecyclerAdapter(private val notes: List<Note>, private val InterfaceListener: MainActivity) :
    RecyclerView.Adapter<NoteRecyclerAdapter.NotesViewHolder>(){

    inner class NotesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val textView = itemView.findViewById<TextView>(R.id.textview)

        fun setData(notes: Note){
            textView.text = notes.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notes_layout, parent, false)
        return NotesViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val notes = notes[position]
        holder.setData(notes)
        val infoBtn = holder.itemView.findViewById<Button>(R.id.openBtn)
        val removeBtn = holder.itemView.findViewById<Button>(R.id.removeBtn)

        infoBtn.setOnClickListener{
            InterfaceListener.onItemClick(position)
        }
        removeBtn.setOnClickListener {
            InterfaceListener.onItemRemove(notes.text)
        }
    }

    override fun getItemCount() = notes.size
}