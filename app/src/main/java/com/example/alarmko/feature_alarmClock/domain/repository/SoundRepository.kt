package com.example.alarmko.feature_alarmClock.domain.repository

import com.example.alarmko.feature_alarmClock.domain.model.AudioItem
import com.example.alarmko.feature_alarmClock.domain.model.RingtoneItem
import kotlinx.coroutines.flow.Flow

interface SoundRepository {

    fun getAlarmSongs() : List<RingtoneItem>
    fun getDeviceSongs() : Flow<List<AudioItem>>
   suspend fun cacheDeviceSongs ()
}