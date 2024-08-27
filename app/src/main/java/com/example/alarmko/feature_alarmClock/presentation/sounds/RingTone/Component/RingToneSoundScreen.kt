package com.example.alarmko.feature_alarmClock.presentation.sounds.RingTone.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.alarmko.feature_alarmClock.domain.model.AudioItem
import com.example.alarmko.feature_alarmClock.domain.model.RingtoneItem
import com.example.compose.cinzelFontFamily

@Composable
fun RingToneSoundScreen(
    modifier: Modifier = Modifier,
    songs: List<RingtoneItem>,
    selectedUri: String,
    onBackPress: () -> Unit,
    onSelectedUriChange: (uri : String,name : String) -> Unit,
    onExternalSounds : () -> Unit

) {
    Column(
        modifier = modifier
            .fillMaxSize()

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackPress) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "navigate back"
                )
            }

            Text(
                fontWeight = FontWeight.Bold,
                fontFamily = cinzelFontFamily,
                text = "Select sound",
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainerHighest, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        onExternalSounds()
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    fontWeight = FontWeight.Bold,
                    fontFamily = cinzelFontFamily,
                    text = "Music on device",
                    fontSize = MaterialTheme.typography.titleSmall.fontSize
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant

                )

            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.background,
                    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
        ) {

            Text(
                fontFamily = cinzelFontFamily,
                modifier = Modifier
                    .padding(16.dp),
                text = "CLASSIC RINGTONES",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            LazyColumn {
                itemsIndexed(songs) { index, audioItem ->
                    RingToneItem(
                        ringtoneItem = audioItem,
                        checked = audioItem.uri == selectedUri,
                        onItemClick = {
                            onSelectedUriChange(audioItem.uri ,audioItem.displayName)
                        }
                    )
                    if (songs.lastIndex != index)
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                }

            }

        }

    }

}

