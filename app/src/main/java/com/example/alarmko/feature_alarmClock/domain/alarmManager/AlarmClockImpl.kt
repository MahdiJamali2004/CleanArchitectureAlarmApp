package com.example.alarmko.feature_alarmClock.domain.alarmManager

import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import com.example.alarmko.feature_alarmClock.domain.receivers.AlarmClockReceiver
import com.example.alarmko.feature_alarmClock.domain.receivers.RepeatingAlarmClockReceiving
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

class AlarmClockImpl(
    private val context: Context
) : AlarmClock {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)


    override fun setSingleAlarmClock(alarmItem: AlarmItem, dayOfWeek: Int?, index: Int?) {
        val calendar = Calendar.getInstance()

        if (dayOfWeek != null) calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek)
        calendar.set(Calendar.HOUR_OF_DAY, alarmItem.time.substring(0, 2).toInt())
        calendar.set(Calendar.MINUTE, alarmItem.time.substring(3, 5).toInt())
        if (alarmItem.time.substring(6, 8).lowercase() == "am") {
            calendar.set(Calendar.AM_PM, Calendar.AM)
        } else {
            calendar.set(Calendar.AM_PM, Calendar.PM)
        }
        calendar.set(Calendar.SECOND, 0)

        if (calendar.before(Calendar.getInstance())) {
            if (alarmItem.listWeeks.isEmpty())
                calendar.add(Calendar.DAY_OF_WEEK, 1)
            else
                calendar.add(Calendar.WEEK_OF_YEAR,1)
        }


        val intent = Intent(context, AlarmClockReceiver::class.java).apply {
            putExtra("alarmItem", alarmItem)
        }

        //if it has several days we set id as hashCode + index
        val id = alarmItem.id!!
        val pendingIntentId =
            if (index == null)
                id
            else {
                (id * 100) + index
            }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            pendingIntentId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmInfo = AlarmClockInfo(
            calendar.timeInMillis,
            pendingIntent
        )


        alarmManager.setAlarmClock(
            alarmInfo,
            pendingIntent
        )

    }

    override fun setRepeatingAlarmClock(alarmItem: AlarmItem) {
        val intent = Intent(context, RepeatingAlarmClockReceiving::class.java).apply {
            putExtra("alarmItem", alarmItem)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmItem.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            AlarmManager.INTERVAL_DAY * 7,
            pendingIntent
        )

    }

    override fun updateAlarmClockSnoozed(alarmItem: AlarmItem, index: Int?) {
        val intent = Intent(context, AlarmClockReceiver::class.java).apply {
            putExtra("alarmItem", alarmItem)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            Int.MAX_VALUE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmInfo = AlarmClockInfo(
            LocalDateTime.now().plusMinutes("${alarmItem.snoozeDuration}".toLong())
                .atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            pendingIntent
        )



        alarmManager.setAlarmClock(
            alarmInfo,
            pendingIntent
        )
    }

    override fun cancelAlarmClock(alarmItem: AlarmItem) {

        val id = alarmItem.id!!
        if (alarmItem.listWeeks.isNotEmpty()) {
//
//            alarmManager.cancel(
//                PendingIntent.getBroadcast(
//                    context,
//                    alarmItem.hashCode(),
//                    Intent(context, RepeatingAlarmClockReceiving::class.java),
//                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//
//                )
//            )
            alarmItem.listWeeks.forEachIndexed { index, day ->
                alarmManager.cancel(
                    PendingIntent.getBroadcast(
                        context,
                        (id * 100) + index,
                        Intent(context, AlarmClockReceiver::class.java),
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

                    )
                )
            }

        } else {
            alarmManager.cancel(
                PendingIntent.getBroadcast(
                    context,
                    id,
                    Intent(context, AlarmClockReceiver::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

                )
            )

        }
    }


}