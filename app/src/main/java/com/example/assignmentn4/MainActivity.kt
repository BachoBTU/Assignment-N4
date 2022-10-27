package com.example.assignmentn4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity(), InterfaceListener{
    private lateinit var recyclerView: RecyclerView
    private lateinit var inputText : EditText
    private lateinit var addBtn : Button
    private lateinit var notesArray: ArrayList<Note>
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.init()
        sharedPreferences = getSharedPreferences("mySharedPreference", Context.MODE_PRIVATE)
        this.getData()

        recyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        var adapter = NoteRecyclerAdapter(notesArray, this)
        recyclerView.adapter = adapter

        addBtn.setOnClickListener {
            this.addNote()

            adapter = NoteRecyclerAdapter(notesArray, this)
            recyclerView.adapter = adapter
        }
    }

    private fun init(){
        inputText = findViewById(R.id.inputText)
        addBtn = findViewById(R.id.addBtn)

        notesArray = ArrayList()
    }

    private fun getData() {
        val gson = Gson()
        val json = sharedPreferences.getString("notes", null)
        val notesArrayType = object : TypeToken<ArrayList<Note>>() {}.type
        if(json != null){
            val cacheArray: ArrayList<Note> = gson.fromJson(json, notesArrayType)
            notesArray = cacheArray
        }
    }
    private fun addNote() : List<Note>{
        val note = inputText.text.toString()

        if(note.isEmpty()){
            Toast.makeText(this, "Field is empty!", Toast.LENGTH_SHORT).show()
        }else {
            notesArray.add(Note(1, note))

            val gson = Gson()
            val json = gson.toJson(notesArray)
            sharedPreferences.edit().putString("notes", json).apply()

            inputText.setText("")
            return notesArray
        }
        return notesArray
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, NoteInfo::class.java)
        intent.putExtra("text", notesArray[position].text )
        startActivity(intent)
    }
    override fun onItemRemove(position: String) {
        val filteredArray = notesArray.filter {
            it.text != position
        }
        notesArray = filteredArray as ArrayList<Note>
        val adapter = NoteRecyclerAdapter(notesArray, this)
        recyclerView.adapter = adapter
        val gson = Gson()
        val json = gson.toJson(notesArray)
        sharedPreferences.edit().putString("notes", json).apply()
    }
}