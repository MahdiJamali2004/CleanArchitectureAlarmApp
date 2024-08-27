package com.example.alarmko.feature_alarmClock.presentation.addEditAlarm

sealed class AlarmEvents {
    data class LabelChange(val label : String) : AlarmEvents()
    data class TimeChange(val time : String) : AlarmEvents()
    data class SnoozeDurationChange(val duration : Int) : AlarmEvents()
    data class VibrationEnable(val isEnable : Boolean) : AlarmEvents()
    data class ListWeeksChange(val listWeeks : List<String>) : AlarmEvents()
    data class AlarmEnableChange(val isEnable: Boolean) : AlarmEvents()
    data class RingDurationChange(val duration : Int) : AlarmEvents()
    data object InsertAlarm: AlarmEvents()

}