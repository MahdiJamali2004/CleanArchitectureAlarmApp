package com.example.alarmko.feature_alarmClock.data.repository

import com.example.alarmko.feature_alarmClock.data.Sounds.ExternalSounds
import com.example.alarmko.feature_alarmClock.data.Sounds.RingtoneSounds
import com.example.alarmko.feature_alarmClock.data.local.LocalDataBase
import com.example.alarmko.feature_alarmClock.domain.model.AudioItem
import com.example.alarmko.feature_alarmClock.domain.model.RingtoneItem
import com.example.alarmko.feature_alarmClock.domain.repository.SoundRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SoundRepositoryImpl @Inject constructor(
    private val dataBase: LocalDataBase,
    private val externalSounds: ExternalSounds,
    private val ringtoneSounds: RingtoneSounds
): SoundRepository {
    override fun getAlarmSongs(): List<RingtoneItem> {
        return  ringtoneSounds.getRingToneSounds()
    }

    override  fun getDeviceSongs(): Flow<List<AudioItem>> {
        return dataBase.audioItemDao.getAllAudios()
    }

    override suspend fun cacheDeviceSongs() {
        val songs = externalSounds.getExternalSounds()
        dataBase.audioItemDao.upsertAudios(songs)
    }
}