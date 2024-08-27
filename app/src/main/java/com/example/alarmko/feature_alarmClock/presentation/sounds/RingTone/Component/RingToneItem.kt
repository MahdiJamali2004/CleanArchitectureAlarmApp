package com.example.alarmko.feature_alarmClock.presentation.sounds.RingTone.Component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.alarmko.feature_alarmClock.domain.model.AudioItem
import com.example.alarmko.feature_alarmClock.domain.model.RingtoneItem
import com.example.compose.cinzelFontFamily

@Composable
fun RingToneItem(
    modifier: Modifier = Modifier,
    ringtoneItem: RingtoneItem,
    checked: Boolean,
    onItemClick : () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            }
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            fontWeight = FontWeight.Bold,
            fontFamily = cinzelFontFamily,
            text = ringtoneItem.displayName,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )

        RadioButton(selected = checked, onClick = onItemClick)
    }

}