package com.example.alarmko.feature_alarmClock.presentation.MainAlarm.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.alarmko.feature_alarmClock.presentation.MainAlarm.MainAlarmEvents
import com.example.alarmko.feature_alarmClock.presentation.MainAlarm.MainAlarmViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun RootMainAlarmScreen(
    modifier: Modifier = Modifier,
    mainAlarmViewModel: MainAlarmViewModel = hiltViewModel(),
    onAlarmItemClick : (id :Int) -> Unit ,
    onInsertAlarmItem : () -> Unit
) {
    MainAlarmScreen(
        modifier = modifier,
        alarmItems = mainAlarmViewModel.alarmItems.collectAsState().value,
        currentTime =mainAlarmViewModel.currentTime.collectAsState(initial = DateTimeFormatter.ofPattern("HH:mm:ss a").format(LocalTime.now())).value,
        onAlarmItemClick ={
            onAlarmItemClick(it.id!!)
        },
        onEnableChange = { index , enable ->
            mainAlarmViewModel.onEvents(MainAlarmEvents.AlarmItemEnableChange(index,enable))
        },
        onInsertAlarmItem = onInsertAlarmItem,
        onDeleteAlarm = {index, alarmItem ->
            mainAlarmViewModel.onEvents(MainAlarmEvents.DeleteAlarmItem(index,alarmItem))
        }
    )

}