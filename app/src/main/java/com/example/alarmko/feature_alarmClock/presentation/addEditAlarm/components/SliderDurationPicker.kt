package com.example.alarmko.feature_alarmClock.presentation.addEditAlarm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.compose.cinzelFontFamily


@Composable
fun DurationSliderPick(
    modifier: Modifier = Modifier,
    title: String,
    hintTitle: String,
    value: Float,
    onDismissRequest: () -> Unit,
    onCancelClick: () -> Unit,
    onOkClick: (Int) -> Unit
) {
    var valueState by remember {
        mutableFloatStateOf(value)
    }
    Dialog(onDismissRequest = {
        onDismissRequest()
    }) {
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {

            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                text = title,
                fontFamily = cinzelFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                text = hintTitle,
                fontFamily = cinzelFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = Color.Gray
            )
            Column {
                Slider(
                    modifier = Modifier
                        .fillMaxWidth()
                        ,
                    value = valueState,
                    onValueChange = { valueState = it },
                    steps = 4,
                    valueRange = 5f..30f
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    for (num in 5..30 step 5) {
                        Text(
                            text = "$num",
                            fontFamily = cinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.labelMedium.fontSize
                        )
                    }

                }
            }

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
                VerticalDivider(
                    modifier = Modifier
                        .height(32.dp)
                        .padding(horizontal = 8.dp)
                )
                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onOkClick(valueState.toInt())

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

