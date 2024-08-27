package com.example.alarmko.feature_alarmClock.presentation.addEditAlarm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.compose.cinzelFontFamily


@Composable
fun LabelDialog(
    modifier: Modifier = Modifier,
    label: String,
    onLabelChange : (String) -> Unit,
    onDismissRequest: () -> Unit,
    onCancelClick: () -> Unit,
    onOkClick: (String) -> Unit
) {

    Dialog(onDismissRequest = onDismissRequest) {
        Column(

            modifier = modifier
                .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(16.dp))
                .padding(16.dp)

        ) {

            Text(
                fontFamily = cinzelFontFamily,

                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                text = "Label",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                placeholder = {
                    Text(text = "Enter label...")
                },
                label = {
                    Text(text = "Label")
                },
                value = label,
                onValueChange = onLabelChange
                , textStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontFamily = cinzelFontFamily
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(modifier = Modifier.weight(1f), onClick = onCancelClick) {
                    Text(
                        fontFamily = cinzelFontFamily,

                        text = "CANCEL",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                VerticalDivider(modifier = Modifier
                        .height(32.dp)
                        .padding(horizontal = 8.dp))
                TextButton(
                    modifier = Modifier.weight(1f),
                    enabled = label.isNotBlank(),
                    onClick = {
                        onOkClick(label)

                    }
                ) {
                    Text(
                        fontFamily = cinzelFontFamily,

                        text = "OK",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }


    }

}
