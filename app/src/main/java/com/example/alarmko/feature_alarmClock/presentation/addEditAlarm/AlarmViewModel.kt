package com.example.alarmko.feature_alarmClock.presentation.addEditAlarm

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.alarmko.feature_alarmClock.data.Sounds.ExternalSounds
import com.example.alarmko.feature_alarmClock.data.Sounds.RingtoneSounds
import com.example.alarmko.feature_alarmClock.domain.alarmManager.AlarmClock
import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import com.example.alarmko.feature_alarmClock.domain.repository.AlarmRepository
import com.example.alarmko.feature_alarmClock.presentation.util.Screens.AddEditAlarmScreen
import com.example.alarmko.feature_alarmClock.presentation.util.Screens.MainAlarmScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    ringtoneSounds: RingtoneSounds,
    private val alarmClock: AlarmClock,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val receivedData = savedStateHandle.toRoute<AddEditAlarmScreen>()

    init {


        viewModelScope.launch {

            if (receivedData.id != null) {
                Log.v(
                    "alarm id is",
                    "alarm id is ${savedStateHandle.toRoute<AddEditAlarmScreen>().id}"
                )
                val alarmItem = alarmRepository.getAlarmItemById(receivedData.id.toInt())
                _alarmState.value = alarmState.value.copy(
                    time = alarmItem.time,
                    listWeeks = alarmItem.listWeeks,
                    isVibrateEnable = alarmItem.isVibrateEnable,
                    sound = alarmItem.sound,
                    soundName = alarmItem.soundName,
                    snoozeDuration = alarmItem.snoozeDuration,
                    ringDuration = alarmItem.ringDuration,
                    isAlarmEnable = alarmItem.isAlarmEnable,
                    label = alarmItem.label
                )
            }
        }
    }

    private val defaultAudioItem = ringtoneSounds.getRingToneSounds()[0]
    private val _alarmState = MutableStateFlow(
        AlarmState(
            soundName = defaultAudioItem.displayName,
            sound = defaultAudioItem.uri
        )
    )
    val alarmState = _alarmState.asStateFlow()


    fun onEvents(alarmEvents: AlarmEvents) {
        when (alarmEvents) {

            is AlarmEvents.AlarmEnableChange -> {
                _alarmState.value = alarmState.value.copy(
                    isAlarmEnable = alarmEvents.isEnable
                )
            }


            is AlarmEvents.LabelChange -> {
                _alarmState.value = alarmState.value.copy(
                    label = alarmEvents.label
                )
            }

            is AlarmEvents.ListWeeksChange -> {
                _alarmState.value = alarmState.value.copy(
                    listWeeks = alarmEvents.listWeeks
                )
            }


            is AlarmEvents.SnoozeDurationChange -> {
                _alarmState.value = alarmState.value.copy(
                    snoozeDuration = alarmEvents.duration
                )
            }

            is AlarmEvents.VibrationEnable -> {
                _alarmState.value = alarmState.value.copy(
                    isVibrateEnable = alarmEvents.isEnable
                )
            }

            is AlarmEvents.RingDurationChange -> {
                _alarmState.value = alarmState.value.copy(
                    ringDuration = alarmEvents.duration
                )
            }

            is AlarmEvents.TimeChange -> {
                _alarmState.value = alarmState.value.copy(
                    time = alarmEvents.time
                )
            }

            AlarmEvents.InsertAlarm -> {
                try {
                    viewModelScope.launch {
                        val alarmItem =
                            AlarmItem(
                                label = alarmState.value.label,
                                time = alarmState.value.time,
                                sound = alarmState.value.sound,
                                soundName = alarmState.value.soundName,
                                isVibrateEnable = alarmState.value.isVibrateEnable,
                                listWeeks = alarmState.value.listWeeks,
                                isAlarmEnable = false,
                                ringDuration = alarmState.value.ringDuration,
                                snoozeDuration = alarmState.value.snoozeDuration,
                                id = savedStateHandle.toRoute<AddEditAlarmScreen>().id?.toInt()
                            )
                        alarmRepository.upsertAlarmItem(
                            alarmItem
                        )
                    }

                } catch (e: Exception) {
                    Log.v("Error", e.stackTrace.toString())
                }
            }
        }
    }

    fun songChange(uri: String, name: String) {
        _alarmState.value = alarmState.value.copy(
            soundName = name,
            sound = uri
        )
    }

}