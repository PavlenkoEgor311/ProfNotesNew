package com.example.profnotes.data.local

import android.content.Context
import androidx.core.content.edit

class Prefs (context: Context) {
    private val authPrefs = context.getSharedPreferences(AUTH_PREFS_NAME,Context.MODE_PRIVATE)

    var isFirstEnter :Boolean
    get() = authPrefs.getBoolean(IS_FIRST_ENTER,true)
    set(value){
        authPrefs.edit { putBoolean(IS_FIRST_ENTER, value) }
    }

    companion object{
        const val AUTH_PREFS_NAME= "auth_prefs"
        const val IS_FIRST_ENTER= "is_first_enter"
    }
}