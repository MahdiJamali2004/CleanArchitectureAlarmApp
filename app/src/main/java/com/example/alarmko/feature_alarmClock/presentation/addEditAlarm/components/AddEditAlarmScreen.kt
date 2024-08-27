package com.example.alarmko.feature_alarmClock.presentation.addEditAlarm.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.TimeFormat
import com.example.alarmko.feature_alarmClock.presentation.addEditAlarm.AlarmState
import com.example.compose.cinzelFontFamily
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun AddEditAlarmScreen(
    modifier: Modifier = Modifier,
    alarmItem: AlarmState,
    onCloseAlarm: () -> Unit,
    onInsertItem: () -> Unit,
    onTimeChange: (String) -> Unit,
    onVibrateChange: (Boolean) -> Unit,
    onListWeeksChange: (List<String>) -> Unit,
    onLabelChange : (String) -> Unit,
    onRingDurationChange : (Int) -> Unit,
    onSnoozeDurationChange : (Int) -> Unit,
    onSelectSoundClick : () -> Unit
    ) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(onClick = onCloseAlarm) {
                    Icon(
                        imageVector = Icons.Default.Close, contentDescription = "Close Alarm"
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    fontFamily = cinzelFontFamily,
                    text = "Add alarm",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
            }


            IconButton(onClick = onInsertItem) {
                Icon(
                    imageVector = Icons.Default.Check, contentDescription = "Close Alarm"
                )
            }


        }
        // LocalTime.parse(alarmItem.time, DateTimeFormatter.ofPattern("HH:mm a"))
        val configuration = LocalConfiguration.current
        WheelTimePicker(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            size = DpSize(configuration.screenWidthDp.dp,(0.2 * configuration.screenHeightDp).dp),
            rowCount = 3,
            timeFormat = TimeFormat.AM_PM,

            startTime = LocalTime.parse(alarmItem.time, DateTimeFormatter.ofPattern("HH:mm a")))
         {

            onTimeChange(it.format(DateTimeFormatter.ofPattern("HH:mm a")))
        }

        var showRepeatState by remember {
            mutableStateOf(false)
        }

        AlarmOptionItem(
            option = "Repeat",
            value = when (alarmItem.listWeeks.size) {
                0 -> "Only ring once"
                7 -> "Every day"
                else -> {
                    alarmItem.listWeeks.map {
                        "$it "
                    }.reduce { acc, word -> acc + word }.dropLast(1)
                }

            },
            onOptionItemClick = { showRepeatState = true }
        )
        if (showRepeatState)
            ListWeeksDialog(
                weekDays = alarmItem.listWeeks,
                onDismissRequest = { showRepeatState = false },
                onCancelClick = { showRepeatState = false },
                onOkClick = { onListWeeksChange(it); showRepeatState = false })



        AlarmOptionItem(
            option = "Sound",
            value = alarmItem.soundName,
            onOptionItemClick = onSelectSoundClick
        )


        var showLabelState by remember {
            mutableStateOf(false)
        }

        AlarmOptionItem(
            option = "Label",
            value = alarmItem.label,
            onOptionItemClick = { showLabelState = true }
        )
        if (showLabelState)
            LabelDialog(
                label = alarmItem.label,
                onDismissRequest = { showLabelState = false },
                onLabelChange = onLabelChange,
                onCancelClick = { showLabelState = false },
                onOkClick = { showLabelState = false}
            )


        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                fontFamily = cinzelFontFamily,
                text = "Vibrate",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )
            Switch(
                modifier = Modifier
                    .scale(0.7f),
                checked = alarmItem.isVibrateEnable,
                onCheckedChange = onVibrateChange
            )

        }

        var showRingDurationState by remember {
            mutableStateOf(false)
        }

        AlarmOptionItem(
            option = "Ring duration",
            value = "${alarmItem.ringDuration} minutes",
            onOptionItemClick = { showRingDurationState = true }
        )
        if (showRingDurationState)
            DurationSliderPick(
                title = "Ring duration",
                hintTitle = "Ring duration(min)",
                value = alarmItem.ringDuration.toFloat(),
                onDismissRequest = { showRingDurationState = false },
                onCancelClick = { showRingDurationState = false },
                onOkClick = { onRingDurationChange(it); showRingDurationState = false }
            )


        var showSnoozeDurationState by remember {
            mutableStateOf(false)
        }

        AlarmOptionItem(
            option = "Snooze duration",
            value = "${alarmItem.snoozeDuration} minutes",
            onOptionItemClick = { showSnoozeDurationState = true }
        )
        if (showSnoozeDurationState)
            DurationSliderPick(
                title = "Snooze duration",
                hintTitle = "Snooze duration(min)",
                value = alarmItem.snoozeDuration.toFloat(),
                onDismissRequest = { showSnoozeDurationState = false },
                onCancelClick = { showSnoozeDurationState = false },
                onOkClick = { onSnoozeDurationChange(it); showSnoozeDurationState = false }
            )


    }

}