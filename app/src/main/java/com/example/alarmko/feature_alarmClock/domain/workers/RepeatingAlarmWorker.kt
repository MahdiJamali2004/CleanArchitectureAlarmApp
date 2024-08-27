package com.example.alarmko.feature_alarmClock.domain.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.alarmko.feature_alarmClock.domain.alarmManager.AlarmClock
import com.example.alarmko.feature_alarmClock.domain.alarmManager.AlarmClockImpl
import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.Calendar
import javax.inject.Inject

@HiltWorker
class RepeatingAlarmWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val alarmClock: AlarmClock,
) : Worker(context, workerParameters) {

    private val mapToCalendarDay = mapOf(
        "sunday" to Calendar.SUNDAY,
        "monday" to Calendar.MONDAY,
        "tuesday" to Calendar.TUESDAY,
        "wednesday" to Calendar.WEDNESDAY,
        "thursday" to Calendar.THURSDAY,
        "friday" to Calendar.FRIDAY,
        "saturday" to Calendar.SATURDAY,
    )

    override fun doWork(): Result {
        try {

            val gson = Gson()
            val alarmItem =
                gson.fromJson(inputData.getString(ALARM_ITEM_KEY), AlarmItem::class.java)
            Log.v("workerResulut", "worker worked. alarmItem = ${alarmItem.listWeeks}")

            alarmItem?.listWeeks?.forEachIndexed { index, day ->
                alarmClock.setSingleAlarmClock(alarmItem, mapToCalendarDay[day.lowercase()], index)
            }
//            Log.v("workerResulut", "worker worked. alarmItem = ${alarmItem.listWeeks}")

            return Result.success()
        } catch (e: Exception) {
            Log.v("workerResulut", e.message ?: "unknown error")
            return Result.failure()
        }

    }

    companion object {
        const val ALARM_ITEM_KEY = "alarmItem"
    }
}