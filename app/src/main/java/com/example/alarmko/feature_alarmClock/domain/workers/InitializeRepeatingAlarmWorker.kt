package com.example.alarmko.feature_alarmClock.domain.workers

import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import com.example.alarmko.feature_alarmClock.domain.repository.AlarmRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class InitializeRepeatingAlarmWorker @Inject constructor(
    private val workManger: WorkManager
) {

    suspend fun initialize(alarmItemsState: Flow<List<AlarmItem>>) {


        withContext(Dispatchers.IO) {
            val gson = Gson()
            Log.v("workerResulut", "initializer worked ")
            alarmItemsState.collect{alarmItems ->

                alarmItems.forEachIndexed { index, alarmItem ->
                    if (alarmItem.isAlarmEnable && alarmItem.listWeeks.isNotEmpty()) {

                        val encodedAlarmItem = gson.toJson(alarmItem)
                        val periodicWork = PeriodicWorkRequestBuilder<RepeatingAlarmWorker>(
                            7,
                            TimeUnit.DAYS
                        ).apply {
                            addTag(alarmItem.hashCode().toString())
                            setInputData(
                                workDataOf(
                                    RepeatingAlarmWorker.ALARM_ITEM_KEY to encodedAlarmItem
                                )
                            )

                        }.build()

                        workManger.enqueueUniquePeriodicWork(
                            alarmItem.hashCode().toString(),
                            ExistingPeriodicWorkPolicy.KEEP,
                            periodicWork
                        )
                    }
                }
            }



        }
    }
}