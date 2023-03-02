package com.example.profnotes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import leakcanary.LeakCanary

@HiltAndroidApp
class ProfNotesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        LeakCanary.showLeakDisplayActivityLauncherIcon(true)
    }
}