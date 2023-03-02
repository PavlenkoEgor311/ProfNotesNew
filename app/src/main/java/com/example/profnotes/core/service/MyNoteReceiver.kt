package com.example.profnotes.core.service

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build


class MyNoteReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(p0: Context?, p1: Intent?) {
        val intent = Intent(p0, NotificationLocalNote::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            p0?.startForegroundService(intent)
        } else {
            p0?.startService(intent)
        }
    }
}