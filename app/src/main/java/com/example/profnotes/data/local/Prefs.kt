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

    var authUser:Boolean
        get() = authPrefs.getBoolean(AUTH_USER,false)
        set(value){
            authPrefs.edit { putBoolean(AUTH_USER, value) }
        }

    companion object{
        const val AUTH_PREFS_NAME= "auth_prefs"
        const val IS_FIRST_ENTER= "is_first_enter"
        const val AUTH_USER = "auth_user"
        const val USER_THEME = "user_theme"
    }
}