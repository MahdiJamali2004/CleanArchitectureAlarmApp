package com.example.alarmko.feature_alarmClock.domain.AlarmNotification

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.alarmko.feature_alarmClock.presentation.alarmRingScreen.AlarmRingActivity
import com.example.alarmko.MainActivity
import com.example.alarmko.R
import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import com.example.alarmko.feature_alarmClock.domain.receivers.SnoozeAlarmReceiver
import com.example.alarmko.feature_alarmClock.domain.receivers.StopAlarmReceiver

class AlarmNotification(
    private val context: Context,
) {

    fun createAlarmNotification(alarmItem: AlarmItem): Notification {

        val notification =
            NotificationCompat.Builder(context, AlarmNotificationChannel.ALARM_NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Ring Ring Ring ...")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setFullScreenIntent(pendingIntentFullScreen(alarmItem),true)
                .setContentIntent(pendingIntentMainActivity())
                .addAction(R.drawable.ic_launcher_foreground,"Snooze",pendingIntentSnoozeAction(alarmItem))
                .addAction(R.drawable.ic_launcher_foreground,"Stop",pendingIntentStopAction(alarmItem))
                .build()

        return notification


    }

    private fun pendingIntentFullScreen(alarmItem: AlarmItem) : PendingIntent {
        val intent =  Intent(context, AlarmRingActivity::class.java).apply {
            putExtra("alarmItem",alarmItem)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        return PendingIntent.getActivity(
            context,
            100000 ,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

    }
    private fun pendingIntentSnoozeAction(alarmItem: AlarmItem) : PendingIntent {
        val intent =  Intent(context,SnoozeAlarmReceiver::class.java).apply {
            putExtra("alarmItem",alarmItem)
        }

        return PendingIntent.getBroadcast(
            context,
            100002 ,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

    }
    private fun pendingIntentStopAction(alarmItem: AlarmItem) : PendingIntent {
        val intent =  Intent(context,StopAlarmReceiver::class.java).apply {
            putExtra("alarmItem",alarmItem)
        }

        return PendingIntent.getBroadcast(
            context,
            100003 ,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

    }
    private fun pendingIntentMainActivity() : PendingIntent {
        val intent =  Intent(context,MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        return PendingIntent.getActivity(
            context,
            100001 ,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

    }

}