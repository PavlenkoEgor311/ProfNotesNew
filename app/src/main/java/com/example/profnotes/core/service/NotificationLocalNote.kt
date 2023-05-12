package com.example.profnotes.core.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.profnotes.MainActivity
import com.example.profnotes.R
import com.example.profnotes.data.dao.NotesDao
import com.example.profnotes.data.models.Notes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationLocalNote : LifecycleService() {

    private var job: Job? = null
    private lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var notesDao: NotesDao


    override fun onCreate() {
        super.onCreate()
        notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        checkLocalNote(null)
    }

    private fun getId(): Int = System.currentTimeMillis().toInt()

    private fun getFlags() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        else
            PendingIntent.FLAG_UPDATE_CURRENT

    private fun checkLocalNote(listNotes: List<Notes>? = listOf()) {
        job?.cancel()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val contentIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, getFlags())
        val builder = getNotificationBuilder()

        builder.setSmallIcon(R.drawable.ic_logo)
            .setContentTitle(listNotes?.size.toString())
            .setContentText("ХУЙ")
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)
            .setContentIntent(contentIntent)
            .setSound(null, AudioManager.STREAM_NOTIFICATION)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_CALL)

        if ((listNotes?.size ?: 0) > 2)
            startForeground(getId(), builder.build())

        job = lifecycleScope.launch {
            delay(5000)
            while (true) {
                launch {
                    checkLocalNote(notesDao.getAllNotes())
                }
                delay(5000)
            }
        }
    }


    private fun getNotificationBuilder(): NotificationCompat.Builder =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(
                DEFAULT_CHANNEL_ID,
                "MyNotes",
                NotificationManager.IMPORTANCE_HIGH
            )
            NotificationCompat.Builder(this, DEFAULT_CHANNEL_ID)
        } else NotificationCompat.Builder(this)

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createChannel(channelId: String, name: CharSequence, importance: Int) {
        val notificationChannel = NotificationChannel(channelId, name, importance)
        notificationChannel.apply {
            enableLights(true)
            enableVibration(true)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }
        notificationManager.createNotificationChannel(notificationChannel)
    }


    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
        stopSelf()
    }

    companion object {
        private const val DEFAULT_CHANNEL_ID = "push_channel_notification"
    }
}