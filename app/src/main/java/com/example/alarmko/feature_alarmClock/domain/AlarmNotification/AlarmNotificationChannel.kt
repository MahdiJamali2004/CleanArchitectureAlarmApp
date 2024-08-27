package com.example.alarmko.feature_alarmClock.domain.AlarmNotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class AlarmNotificationChannel(
    private val context: Context
) {

    companion object{
        const val ALARM_NOTIFICATION_CHANNEL = "ALARM_NOTIFICATION_CHANNEL"
    }

    fun createAlarmNotificationChannel(){

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                ALARM_NOTIFICATION_CHANNEL,
                "AlarmClock",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC

            }
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        notificationManager.createNotificationChannel(channel)
    }
}