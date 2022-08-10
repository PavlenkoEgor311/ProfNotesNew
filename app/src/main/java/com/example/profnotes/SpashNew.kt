package com.example.profnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.profnotes.databinding.ActivityMainBinding

lateinit var binding: ActivityMainBinding

class SpashNew : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash_new)
        val i = Intent(this,MainActivity::class.java)
        startActivity(i)
    }
}