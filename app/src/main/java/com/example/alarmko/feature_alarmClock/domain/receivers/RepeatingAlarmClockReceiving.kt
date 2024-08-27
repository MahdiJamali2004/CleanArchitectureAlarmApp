package com.example.alarmko.feature_alarmClock.domain.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.alarmko.feature_alarmClock.domain.alarmManager.AlarmClock
import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class RepeatingAlarmClockReceiving : BroadcastReceiver() {

    @Inject
    lateinit var alarmClock: AlarmClock
    private val mapToCalendarDay = mapOf(
        "sunday" to Calendar.SUNDAY,
        "monday" to Calendar.MONDAY,
        "tuesday" to Calendar.TUESDAY,
        "wednesday" to Calendar.WEDNESDAY,
        "thursday" to Calendar.THURSDAY,
        "friday" to Calendar.FRIDAY,
        "saturday" to Calendar.SATURDAY,
    )
    override fun onReceive(context: Context?, intent: Intent) {


        val alarmItem: AlarmItem? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("alarmItem",AlarmItem::class.java)
            } else {
                intent.getParcelableExtra<AlarmItem>("alarmItem")
            }


        alarmItem?.listWeeks?.forEachIndexed { index,day  ->
            alarmClock.setSingleAlarmClock(alarmItem, mapToCalendarDay[day.lowercase()] , index)
        }

    }
}