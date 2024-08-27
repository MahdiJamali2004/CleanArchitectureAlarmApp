package com.example.alarmko.feature_alarmClock.presentation.sounds.ExternalSound.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.alarmko.R
import com.example.alarmko.feature_alarmClock.domain.model.AudioItem
import com.example.compose.cinzelFontFamily

@Composable
fun ExternalSongItem(
    modifier: Modifier = Modifier,
    audioItem: AudioItem,
    checked: Boolean,
    onItemClick: () -> Unit
) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            }
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(audioItem.imgUri ?: Uri.parse("android.resource://${context.packageName}/drawable/music_default"))
                        .crossfade(true)
                        .build(),
                    contentDescription = "song image",
                    contentScale = if (audioItem.imgUri == null)  ContentScale.Crop else  ContentScale.Fit,
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                )
            Spacer(modifier = Modifier.width(6.dp))

            Text(
                fontWeight = FontWeight.Bold,
                fontFamily = cinzelFontFamily,
                modifier = Modifier.fillMaxWidth(0.8f),
                text = audioItem.displayName,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }


        RadioButton(
            selected = checked,
            onClick = onItemClick
        )
    }


}