package com.example.alarmko.feature_alarmClock.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class AudioItem(
    @PrimaryKey(false)
    val id :Long,
    val displayName : String,
    val uri : String,
    val imgUri: String?
)
