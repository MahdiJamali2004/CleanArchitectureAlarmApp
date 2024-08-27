package com.example.alarmko.feature_alarmClock.data.repository

import com.example.alarmko.feature_alarmClock.data.local.LocalDataBase
import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import com.example.alarmko.feature_alarmClock.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val localDataBase: LocalDataBase
) : AlarmRepository {
    override fun getAllAlarmItems(): Flow<List<AlarmItem>> {
        return localDataBase.alarmDao.getAllAlarms()
    }

    override suspend fun getAlarmItemById(id: Int): AlarmItem {
        return localDataBase.alarmDao.getAlarmById(id)
    }

    override suspend fun upsertAlarmItem(alarmItem: AlarmItem) {
      localDataBase.alarmDao.upsertAlarm(alarmItem)
    }

    override suspend fun deleteAlarmItem(alarmItem: AlarmItem) {
        localDataBase.alarmDao.deleteAlarm(alarmItem)
    }
}