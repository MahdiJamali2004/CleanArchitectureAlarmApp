package com.example.alarmko.feature_alarmClock.presentation.MainAlarm

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.alarmko.feature_alarmClock.domain.alarmManager.AlarmClock
import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import com.example.alarmko.feature_alarmClock.domain.repository.AlarmRepository
import com.example.alarmko.feature_alarmClock.domain.workers.InitializeRepeatingAlarmWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainAlarmViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmClock: AlarmClock,
    private val initializeRepeatingAlarmWorker: InitializeRepeatingAlarmWorker,
    private val workManager: WorkManager
) : ViewModel() {


    private var _alarmItems = MutableStateFlow(emptyList<AlarmItem>())
    val alarmItems = _alarmItems.asStateFlow()


    val currentTime = flow<String> {
        while (true) {
            emit(DateTimeFormatter.ofPattern("HH:mm:ss a").format(LocalTime.now()))
            delay(1000)
        }
    }


    init {

        viewModelScope.launch {
            alarmRepository.getAllAlarmItems().collect {
                _alarmItems.value = it

            }
        }

    }

    fun onEvents(mainAlarmEvents: MainAlarmEvents) {
        when (mainAlarmEvents) {
            is MainAlarmEvents.AlarmItemEnableChange -> {
                if (mainAlarmEvents.enable) {
                    if (alarmItems.value[mainAlarmEvents.index].listWeeks.isEmpty()) {
                        alarmClock.setSingleAlarmClock(alarmItems.value[mainAlarmEvents.index])
                    } else {
                        viewModelScope.launch {

                            initializeRepeatingAlarmWorker.initialize(alarmItems)
                        }
                    }
                } else {
                    alarmClock.cancelAlarmClock(alarmItems.value[mainAlarmEvents.index])
                    workManager.cancelUniqueWork(
                        alarmItems.value[mainAlarmEvents.index].hashCode().toString()
                    )
                }

                _alarmItems.value = alarmItems.value.toMutableList().apply {
                    this[mainAlarmEvents.index] = this[mainAlarmEvents.index].copy(
                        isAlarmEnable = mainAlarmEvents.enable
                    )
                }
                viewModelScope.launch {

                    alarmRepository.upsertAlarmItem(
                        alarmItem = alarmItems.value[mainAlarmEvents.index].copy(
                            isAlarmEnable = mainAlarmEvents.enable
                        )
                    )
                }
            }

            is MainAlarmEvents.DeleteAlarmItem -> {
                viewModelScope.launch {
                    alarmRepository.deleteAlarmItem(mainAlarmEvents.alarmItem)
                    alarmClock.cancelAlarmClock(alarmItems.value[mainAlarmEvents.index])
                    workManager.cancelUniqueWork(
                        alarmItems.value[mainAlarmEvents.index].hashCode().toString()
                    )
                }
            }
        }
    }


}