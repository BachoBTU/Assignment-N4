package com.example.assignmentn4

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NoteInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_info_layout)

        val infoText = findViewById<TextView>(R.id.infoText)

        val text = intent.getStringExtra("text" )
        infoText.text = text.toString()
    }
}