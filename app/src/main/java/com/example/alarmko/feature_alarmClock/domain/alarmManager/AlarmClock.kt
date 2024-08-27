package com.example.alarmko.feature_alarmClock.domain.alarmManager

import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import com.example.alarmko.feature_alarmClock.domain.model.AudioItem

interface AlarmClock {

     fun setSingleAlarmClock(alarmItem: AlarmItem , dayOfWeek : Int? = null, index : Int? =null)
     fun setRepeatingAlarmClock(alarmItem: AlarmItem)
     fun updateAlarmClockSnoozed(alarmItem: AlarmItem , index: Int? =null)
     fun cancelAlarmClock(alarmItem: AlarmItem)
}