package com.nodil.diplom.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat


class NotificationHelper(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "My App Notification Channel"
        val descriptionText = "Notification channel for My App"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("my_app_channel", name, importance).apply {
            description = descriptionText
        }
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val att = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()
        channel.enableLights(true);
        channel.lightColor = Color.RED;
        channel.vibrationPattern = longArrayOf(0, 500, 200, 500);
        channel.enableVibration(true);
        channel.setSound(soundUri, att)
        channel.importance = NotificationManager.IMPORTANCE_HIGH
        notificationManager.createNotificationChannel(channel)
    }

    fun showNotification(title: String, message: String) {
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, "my_app_channel")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation    )
            .setContentTitle(title)
            .setContentText(message)
            .setSound(soundUri)
            .setVibrate(longArrayOf(0, 500, 200, 500))
            .setDefaults(NotificationCompat.DEFAULT_SOUND)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        notificationManager.notify(0, notificationBuilder.build())
    }
}