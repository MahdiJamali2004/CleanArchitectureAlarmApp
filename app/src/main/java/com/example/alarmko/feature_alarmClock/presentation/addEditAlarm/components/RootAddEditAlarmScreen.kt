package com.example.alarmko.feature_alarmClock.presentation.addEditAlarm.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.alarmko.feature_alarmClock.presentation.addEditAlarm.AlarmEvents
import com.example.alarmko.feature_alarmClock.presentation.addEditAlarm.AlarmViewModel
import com.example.alarmko.feature_alarmClock.presentation.util.Screens.RingToneSongScreen

@Composable
fun RootAddEditAlarmScreen(
    modifier: Modifier = Modifier,
    alarmViewModel: AlarmViewModel = hiltViewModel(),
    songUri: String?,
    songName: String?,
    navController: NavController
) {
    LaunchedEffect(true) {
        if (songUri != null && songName != null) {
            alarmViewModel.songChange(songUri, songName)
        }
    }


    AddEditAlarmScreen(
        modifier = modifier,
        alarmItem = alarmViewModel.alarmState.collectAsState().value,
        onCloseAlarm = {
            navController.navigateUp()
        },
        onInsertItem = {
            alarmViewModel.onEvents(AlarmEvents.InsertAlarm)
            navController.navigateUp()
        },
        onTimeChange = {
            alarmViewModel.onEvents(AlarmEvents.TimeChange(it))
        },
        onVibrateChange = {
            alarmViewModel.onEvents(AlarmEvents.VibrationEnable(it))
        },
        onListWeeksChange = {
            alarmViewModel.onEvents(AlarmEvents.ListWeeksChange(it))

        },
        onLabelChange = {
            alarmViewModel.onEvents(AlarmEvents.LabelChange(it))
        },
        onRingDurationChange = {
            alarmViewModel.onEvents(AlarmEvents.RingDurationChange(it))
        },
        onSnoozeDurationChange = {
            alarmViewModel.onEvents(AlarmEvents.SnoozeDurationChange(it))
        },
        onSelectSoundClick = {
//            navController.currentBackStackEntry?.savedStateHandle?.set("songUri",alarmViewModel.alarmState.value.sound)
            navController.navigate(RingToneSongScreen(alarmViewModel.alarmState.value.sound))
        }
    )

}