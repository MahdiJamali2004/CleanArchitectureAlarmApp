package com.example.alarmko.feature_alarmClock.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Query("SELECT * FROM AlarmItem")
    fun getAllAlarms() : Flow<List<AlarmItem>>

    @Query("SELECT * FROM AlarmItem WHERE id = :id")
    suspend fun getAlarmById(id : Int) : AlarmItem

    @Upsert
    suspend fun upsertAlarm(alarmItem: AlarmItem)

    @Delete
    suspend fun deleteAlarm(alarmItem: AlarmItem)





}