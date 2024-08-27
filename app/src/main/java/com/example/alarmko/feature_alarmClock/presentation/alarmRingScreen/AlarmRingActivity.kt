package com.example.alarmko.feature_alarmClock.presentation.alarmRingScreen

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import com.example.alarmko.feature_alarmClock.domain.alarmManager.AlarmClock
import com.example.alarmko.feature_alarmClock.domain.alarmService.AlarmService
import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import com.example.alarmko.feature_alarmClock.presentation.alarmRingScreen.component.AlarmRingScreen
import com.example.compose.AlarmKoTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class AlarmRingActivity : ComponentActivity() {

    @Inject
    lateinit var alarmClock : AlarmClock
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onLockPhone()
        val alarmItem: AlarmItem =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableExtra("alarmItem", AlarmItem::class.java)!!
            } else {
                intent?.getParcelableExtra<AlarmItem>("alarmItem")!!
            }
        enableEdgeToEdge()
        setContent {
            val activity = LocalContext.current as? Activity
            AlarmKoTheme {
                AlarmRingScreen(
                    snoozeMin = alarmItem.snoozeDuration ,
                    onStopAlarm = {
                        this.stopService(Intent(this, AlarmService::class.java))
                        activity?.finish()
                    },
                    onSnoozeAlarm = {
                        this.stopService(Intent(this, AlarmService::class.java))

                        val stringTimePlusDuration = timePlusDurationString(alarmItem)
                        if (alarmItem.listWeeks.isNotEmpty()) {
                            val today = LocalDateTime.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

                            alarmItem.listWeeks.forEachIndexed { index, day ->
                                if(day.lowercase() == today.lowercase()){
                                    alarmClock.setSingleAlarmClock(alarmItem.copy(time = stringTimePlusDuration),index = index)
                                }
                            }
                        } else {
                            alarmClock.setSingleAlarmClock(alarmItem.copy(time = stringTimePlusDuration))
                        }
                        activity?.finish()
                    })
            }
        }
    }

    private fun onLockPhone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this, null)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
            val keyGuardService = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            val keyGuardLock = keyGuardService.newKeyguardLock(packageName)
            keyGuardLock.disableKeyguard()
        }
    }
    private fun timePlusDurationString(alarmItem : AlarmItem?) : String{
        val time = LocalTime.parse(alarmItem?.time, DateTimeFormatter.ofPattern("HH:mm a"))
        val timePlusDuration = time.plusMinutes("${alarmItem!!.snoozeDuration}".toLong())
        return  DateTimeFormatter.ofPattern("HH:mm a").format(timePlusDuration)
    }
}

