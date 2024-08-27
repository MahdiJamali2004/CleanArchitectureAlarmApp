package com.example.alarmko.feature_alarmClock.domain.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.alarmko.feature_alarmClock.domain.alarmManager.AlarmClock
import com.example.alarmko.feature_alarmClock.domain.alarmService.AlarmService
import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class SnoozeAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmClock: AlarmClock

    override fun onReceive(context: Context, intent: Intent?) {

        val alarmItem: AlarmItem? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableExtra("alarmItem", AlarmItem::class.java)
            } else {
                intent?.getParcelableExtra<AlarmItem>("alarmItem")
            }

//        alarmClock.cancelAlarmClock(alarmItem!!)
        context.stopService(Intent(context, AlarmService::class.java))



        val stringTimePlusDuration = timePlusDurationString(alarmItem)
        if (alarmItem!!.listWeeks.isNotEmpty()) {
            val today = LocalDateTime.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

            alarmItem.listWeeks.forEachIndexed { index, day ->
                if(day.lowercase() == today.lowercase()){
                      alarmClock.setSingleAlarmClock(alarmItem.copy(time = stringTimePlusDuration),index = index)
                }
            }
        } else {
            alarmClock.setSingleAlarmClock(alarmItem.copy(time = stringTimePlusDuration))
        }

    }
    private fun timePlusDurationString(alarmItem : AlarmItem?) : String{
        val time = LocalTime.parse(alarmItem?.time,DateTimeFormatter.ofPattern("HH:mm a"))
        val timePlusDuration = time.plusMinutes("${alarmItem!!.snoozeDuration}".toLong())
       return  DateTimeFormatter.ofPattern("HH:mm a").format(timePlusDuration)
    }
}