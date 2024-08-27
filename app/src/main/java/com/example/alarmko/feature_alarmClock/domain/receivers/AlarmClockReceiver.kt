package com.example.alarmko.feature_alarmClock.domain.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import com.example.alarmko.feature_alarmClock.domain.AlarmNotification.AlarmNotification
import com.example.alarmko.feature_alarmClock.domain.alarmService.AlarmService
import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import com.example.alarmko.feature_alarmClock.domain.repository.AlarmRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class AlarmClockReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmNotification: AlarmNotification

    @Inject
    lateinit var alarmRepository: AlarmRepository

    override fun onReceive(context: Context, intent: Intent?) {

        val alarmItem: AlarmItem =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableExtra("alarmItem", AlarmItem::class.java)!!
            } else {
                intent?.getParcelableExtra<AlarmItem>("alarmItem")!!
            }
        val serviceIntent = Intent(context, AlarmService::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("alarmItem", alarmItem)
        }
        CoroutineScope(Dispatchers.IO).launch {

            if(alarmItem.listWeeks.isEmpty()){
                alarmRepository.upsertAlarmItem(alarmItem.copy(
                    isAlarmEnable = false
                ))
            }

        }

        wakeLockPhone(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }

    }

    private fun wakeLockPhone(context: Context) {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!powerManager.isInteractive) {

            val wakeLock = powerManager.newWakeLock(
                PowerManager.FULL_WAKE_LOCK or
                        PowerManager.ACQUIRE_CAUSES_WAKEUP or
                        PowerManager.ON_AFTER_RELEASE, "Alarm Ko::WakeLock"
            )
            wakeLock.acquire(10 * 60 * 1000L )

        }

    }
}