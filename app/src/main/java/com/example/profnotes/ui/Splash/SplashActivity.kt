package com.example.profnotes.ui.Splash

import androidx.activity.viewModels
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.profnotes.MainActivity
import com.example.profnotes.viewmodel.SplashViewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.profnotes.data.models.Notes
import com.example.profnotes.data.models.util.ResponseWrapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val viewModel:SplashViewModel by viewModels()

    private val firstSetupNotes: List<Notes> = listOf(
        Notes(
            id = 0,
            title = "One",
            date = "Today",
            status = "Новое",
            description = "Test"
        ),
        Notes(
            id = 0,
            title = "Two",
            date = "Today",
            status = "Новое",
            description = "Test"
        ),
        Notes(
            id = 0,
            title = "Three",
            date = "Today",
            status = "Завершено",
            description = "Test"
        ),
        Notes(
            id = 0,
            title = "Test",
            date = "Today",
            status = "Новое",
            description = "Test"
        ),
        Notes(
            id = 0,
            title = "12",
            date = "Today",
            status = "В работе",
            description = "Test"
        ),
        Notes(
            id = 0,
            title = "123",
            date = "Today",
            status = "В работе",
            description = "Test"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this,
                              viewModel.getIsFirstEnter().toString(),
                              Toast.LENGTH_LONG).show()
        viewModel.setIsFirstEnter(false)
//        lifecycleScope.launch {
//            viewModel.note.collectLatest {
//                    when(it){
//                        is ResponseWrapper.Idle->{}
//                        is ResponseWrapper.Error->{
//                            Log.e("Error","${it.code}")
//                        }
//                        is ResponseWrapper.Success->{
//                            Log.e("Succses","${it.body}")
//                        }
//                    }
//            }
//        }

//        firstSetupNotes.forEach {
//            viewModel.addNote(it)
//        }

        val i = Intent(this,MainActivity::class.java)
        startActivity(i)
        finish()
    }
}

