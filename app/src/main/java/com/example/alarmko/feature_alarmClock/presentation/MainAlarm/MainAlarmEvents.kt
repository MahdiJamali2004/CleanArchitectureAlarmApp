package com.example.alarmko.feature_alarmClock.presentation.MainAlarm

import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem

sealed class MainAlarmEvents {
    data class DeleteAlarmItem(val index: Int,val alarmItem: AlarmItem) : MainAlarmEvents()
    data class AlarmItemEnableChange(val index :Int,val enable :Boolean) : MainAlarmEvents()
}