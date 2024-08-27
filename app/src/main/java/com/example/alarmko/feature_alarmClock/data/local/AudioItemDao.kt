package com.example.alarmko.feature_alarmClock.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.alarmko.feature_alarmClock.domain.model.AudioItem
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioItemDao {
    @Query("SELECT * FROM AudioItem")
    fun getAllAudios():Flow<List<AudioItem>>

    @Upsert
    suspend fun upsertAudios(list: List<AudioItem>)
}