package com.example.alarmko.feature_alarmClock.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.alarmko.feature_alarmClock.data.local.DataBaseConverters
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class AlarmItem(
    val label : String ,
    val time : String, // HH:mm a format
    val sound : String ,
    val soundName : String,
    val isVibrateEnable : Boolean ,
    @TypeConverters(DataBaseConverters::class)
    val listWeeks : List<String>,
    val isAlarmEnable : Boolean,
    val ringDuration : Int,
    val snoozeDuration : Int,
    @PrimaryKey(autoGenerate = true)
    val id :Int? = null

) : Parcelable
