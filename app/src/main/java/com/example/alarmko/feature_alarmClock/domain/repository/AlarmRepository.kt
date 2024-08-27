package com.example.alarmko.feature_alarmClock.domain.repository

import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {


    fun getAllAlarmItems(): Flow<List<AlarmItem>>


    suspend fun getAlarmItemById(id: Int): AlarmItem


    suspend fun upsertAlarmItem(alarmItem: AlarmItem)


    suspend fun deleteAlarmItem(alarmItem: AlarmItem)
}