package com.example.alarmko.feature_alarmClock.presentation.MainAlarm.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.alarmko.R
import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import com.example.compose.cinzelFontFamily

import java.time.Duration

@Composable
fun MainAlarmScreen(
    modifier: Modifier = Modifier,
    alarmItems: List<AlarmItem>,
    currentTime: String,
    onAlarmItemClick: (AlarmItem) -> Unit,
    onEnableChange: (Int, Boolean) -> Unit,
    onDeleteAlarm: (index :Int, AlarmItem) -> Unit,
    onInsertAlarmItem: () -> Unit
) {
    val lazyColumnState = rememberLazyListState()
    var previousIndex by remember { mutableIntStateOf(lazyColumnState.firstVisibleItemIndex) }
    var previousScrollOffset by remember { mutableIntStateOf(lazyColumnState.firstVisibleItemScrollOffset) }

    val isScrollingUp by remember {
        derivedStateOf {
            if (lazyColumnState.firstVisibleItemIndex != previousIndex) {
                previousIndex > lazyColumnState.firstVisibleItemIndex
            } else {
                previousScrollOffset >= lazyColumnState.firstVisibleItemScrollOffset
            }.also {
                previousIndex = lazyColumnState.firstVisibleItemIndex
                previousScrollOffset = lazyColumnState.firstVisibleItemScrollOffset

            }
        }
    }
    Scaffold(modifier = modifier,
        floatingActionButton = {
            if (isScrollingUp)
                ExtendedFloatingActionButton(onClick = onInsertAlarmItem) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_clock),
                        contentDescription = "addAlarm"
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "add alarm",
                        fontFamily = cinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )

                }
        }) {

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp),
            state = lazyColumnState
        ) {
            item {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Alarm Ko",
                        fontFamily = cinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.displaySmall.fontSize
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        fontFamily = cinzelFontFamily,
                        text = currentTime.substring(0, 8),
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.displaySmall.fontSize,
                    )
                    Text(

                        modifier = Modifier
                            .align(Alignment.Bottom),
                        fontWeight = FontWeight.Bold,
                        fontFamily = cinzelFontFamily,
                        text = currentTime.substring(9, 11),
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    )
                }
            }




            itemsIndexed(alarmItems) { index, alarmItem ->
                AlarmItem(
                    modifier = Modifier
                        .padding(16.dp),
                    alarmItem = alarmItem,
                    onDeleteAlarm = {
                        onDeleteAlarm(index,alarmItem)
                    },
                    onAlarmItemClick = {
                        onAlarmItemClick(alarmItem)
                    },
                    onEnabledChange = { enable ->
                        onEnableChange(index, enable)
                    })

            }

        }


    }

}