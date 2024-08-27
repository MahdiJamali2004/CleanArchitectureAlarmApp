package com.example.alarmko.feature_alarmClock.presentation.MainAlarm.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alarmko.R
import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import com.example.compose.cinzelFontFamily


@Composable
fun AlarmItem(
    modifier: Modifier = Modifier,
    alarmItem: AlarmItem,
    onAlarmItemClick: () -> Unit,
    onEnabledChange: (Boolean) -> Unit,
    onDeleteAlarm: () -> Unit
) {

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onAlarmItemClick
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier,
            ) {

                Text(

                    fontFamily = cinzelFontFamily,
                    text = alarmItem.label,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        fontFamily = FontFamily(Font(R.font.cinzel)),
                        text = alarmItem.time.substring(0, 5),
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        fontWeight = FontWeight.Bold

                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        fontFamily = cinzelFontFamily,
                        text = alarmItem.time.substring(6, 8),
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.Bold

                    )

                }

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    alarmItem.listWeeks.forEachIndexed { index, day ->


                        Text(
                            fontFamily = cinzelFontFamily,
                            text = "${day.substring(0, 3)} ",
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.labelSmall.fontSize,
                            color = Color.Gray
                        )

                    }
                }

            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Switch(checked = alarmItem.isAlarmEnable, onCheckedChange = onEnabledChange)
                IconButton(onClick = {
                    onDeleteAlarm()
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "delete alarm")
                }

            }

        }
    }

}

