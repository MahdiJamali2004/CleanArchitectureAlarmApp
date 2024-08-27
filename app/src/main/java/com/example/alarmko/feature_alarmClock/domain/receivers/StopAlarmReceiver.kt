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
import javax.inject.Inject

@AndroidEntryPoint
class StopAlarmReceiver :BroadcastReceiver() {

    @Inject
    lateinit var alarmClock: AlarmClock

    override fun onReceive(context: Context, intent: Intent?) {



        val alarmItem: AlarmItem? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableExtra("alarmItem", AlarmItem::class.java)
            } else {
                intent?.getParcelableExtra<AlarmItem>("alarmItem")
            }


        context.stopService(Intent(context, AlarmService::class.java))
//        alarmClock.cancelAlarmClock(alarmItem!!)

    }
}