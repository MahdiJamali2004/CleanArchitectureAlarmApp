package com.example.alarmko.feature_alarmClock.presentation.addEditAlarm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.compose.cinzelFontFamily


@Composable
fun ListWeeksDialog(
    modifier: Modifier = Modifier,
    weekDays: List<String>,
    onDismissRequest: () -> Unit,
    onCancelClick: () -> Unit,
    onOkClick: (List<String>) -> Unit

) {
    val listDays = listOf(
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday"
    )
    var weekDayState by remember {
        mutableStateOf(weekDays)
    }
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(

        )
    ) {
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                text = "Repeat",
                fontFamily = cinzelFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
            listDays.forEach { day ->
                ListWeekItem(
                    day = day,
                    isChecked = weekDayState.contains(day),
                    onCheckClick = { _ ->
                        weekDayState = if (weekDayState.contains(day)) {
                            weekDayState.toMutableList().apply {
                                remove(day)
                            }.toList()
                        } else {
                            weekDayState.toMutableList().apply {
                                add(day)
                            }.toList()
                        }
                    }
                )

                if (day != listDays[6])
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))

            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(modifier = Modifier.weight(1f), onClick = onCancelClick) {
                    Text(
                        text = "CANCEL",
                        fontFamily = cinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                VerticalDivider(modifier = Modifier
                    .height(32.dp)
                    .padding(horizontal = 8.dp))
                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onOkClick(weekDayState)

                    }
                ) {
                    Text(
                        text = "OK",
                        fontFamily = cinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

        }
    }

}

