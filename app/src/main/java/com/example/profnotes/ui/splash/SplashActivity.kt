package com.example.profnotes.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.profnotes.MainActivity
import com.example.profnotes.data.models.Notes
import com.example.profnotes.data.models.util.ResponseWrapper
import com.example.profnotes.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()
    private val firstSetupNotes: List<Notes> = listOf(
        Notes(
            id = 0,
            title = "test 2",
            date = "Today",
            status = "Новое",
            description = "Test"
        ),
        Notes(
            id = 0,
            title = "test 2",
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
            title = "test 3",
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
            title = "test 1",
            date = "Today",
            status = "В работе",
            description = "Test"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setIsFirstEnter(false)
        lifecycleScope.launch {
            viewModel.note.collectLatest {
                when (it) {
                    is ResponseWrapper.Idle -> {}
                    is ResponseWrapper.Error -> {
                        Log.d("Error", "${it.code}")
                    }
                    is ResponseWrapper.Success -> {
                        Log.d("Success", "${it.body}")
                    }
                }
            }
        }

        if (viewModel.getIsFirstEnter()) {
            firstSetupNotes.forEach {
                viewModel.addNote(it)
            }
        }

        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}

