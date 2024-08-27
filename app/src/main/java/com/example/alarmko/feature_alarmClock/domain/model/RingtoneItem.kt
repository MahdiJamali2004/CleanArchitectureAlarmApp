package com.example.alarmko.feature_alarmClock.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class RingtoneItem(
    val displayName : String,
    val uri : String,
)
