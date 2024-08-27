package com.example.alarmko.feature_alarmClock.presentation.addEditAlarm

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class AlarmState(

    val time: String = DateTimeFormatter.ofPattern("HH:mm a").format(LocalDateTime.now()),
    val listWeeks: List<String> = emptyList(),
    val isVibrateEnable: Boolean = true,
    val sound: String = "",
    val soundName: String = "",
    val snoozeDuration: Int = 5,
    val ringDuration: Int = 5,
    val isAlarmEnable: Boolean = true,
    val label: String = "Alarm"
)
