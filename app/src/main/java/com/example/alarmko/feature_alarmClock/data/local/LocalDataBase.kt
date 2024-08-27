package com.example.alarmko.feature_alarmClock.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import com.example.alarmko.feature_alarmClock.domain.model.AudioItem

@TypeConverters(DataBaseConverters::class)
@Database(
    entities = [
        AlarmItem::class,
        AudioItem::class
    ],
    exportSchema = false,
    version = 1
)
abstract class LocalDataBase : RoomDatabase() {

    abstract val alarmDao : AlarmDao
    abstract val audioItemDao : AudioItemDao

    companion object{
        const val DATABASE = "alarm.db"
    }
}