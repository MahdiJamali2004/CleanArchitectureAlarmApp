package com.example.alarmko.feature_alarmClock.presentation.alarmRingScreen.component

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alarmko.R
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun AlarmRingScreen(
    modifier: Modifier = Modifier,
    snoozeMin: Int,
    onStopAlarm: () -> Unit,
    onSnoozeAlarm: () -> Unit
) {

    val currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm a"))
    val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, dd MMMM"))


    var visibilityState by remember { mutableStateOf(true) }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
    ) {
        AnimatedVisibility(
            visible = visibilityState,
            enter = slideInVertically(tween(1000)) { fullHeight ->
                fullHeight
            },
            exit = slideOutVertically(animationSpec = tween(1000)) { fullHeight ->
                -fullHeight
            }
        ) {
            Box(
                modifier = modifier
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { change, dragAmount ->
                            if (dragAmount <= -30) {
                                visibilityState = false
                                onStopAlarm()

                            }
                        }
                    }
                    .fillMaxSize()
                    .padding(it)
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    modifier = Modifier.align(Alignment.TopCenter),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = currentTime,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.displayMedium.fontSize
                    )
                    Text(
                        text = currentDate,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray,
                        fontSize = MaterialTheme.typography.labelLarge.fontSize
                    )
                }

                Column(
                    modifier = Modifier.align(Alignment.Center),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = stringResource(R.string.alarm),
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onSnoozeAlarm) {
                        Text(
                            text = "Snooze for $snoozeMin minute",
                            fontWeight = FontWeight.Normal,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize
                        )
                    }

                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SwipeUpAnimation()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                       
                        text = "Swipe up to stop alarm",
                        fontWeight = FontWeight.Normal,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )

                }

              

            }

        }
    }


}

@Preview
@Composable
private fun PreviewAlarmRingScreen() {
    AlarmRingScreen(snoozeMin = 5, onSnoozeAlarm = {}, onStopAlarm = {})
}