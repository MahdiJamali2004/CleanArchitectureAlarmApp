package com.example.alarmko.feature_alarmClock.presentation.util.Screens

import kotlinx.serialization.Serializable

@Serializable
object MainAlarmScreen

@Serializable
data class AddEditAlarmScreen(
    val id : String?
)

@Serializable
data class RingToneSongScreen(
    val songUri : String
)

@Serializable
data class ExternalSoundScreen(
    val songUri : String
)
