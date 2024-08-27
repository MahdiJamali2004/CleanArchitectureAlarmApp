package com.example.alarmko.feature_alarmClock.presentation.sounds.ExternalSound.component

import android.app.Activity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.navigation.NavController

@Composable
fun ReadAudioPermission(
    modifier: Modifier = Modifier,
    navController: NavController,
    readAudioPerm : Pair<String,Boolean>,
    permissionQueueState: SnapshotStateList<String>,
    activityResult: ManagedActivityResultLauncher<String, Boolean>,
    onOpenSetting: () -> Unit
) {



    if (permissionQueueState.contains(readAudioPerm.first)) {
        val context = LocalContext.current
        val activity = context as? Activity
        val isPermanentlyDeclined =
            !shouldShowRequestPermissionRationale(activity!!, readAudioPerm.first)
        AlertDialog(
            modifier = modifier,
            onDismissRequest = {
                permissionQueueState.remove(readAudioPerm.first)
                navController.popBackStack()

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (isPermanentlyDeclined) {
                            permissionQueueState.remove(readAudioPerm.first)
                            onOpenSetting()

                        } else {
                            permissionQueueState.remove(readAudioPerm.first)
                            activityResult.launch(readAudioPerm.first)
                        }

                    }
                ) {
                    Text(
                        text = "Confirm",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        permissionQueueState.remove(readAudioPerm.first)
                        navController.popBackStack()
                    }
                ) {
                    Text(
                        text = "Dismiss",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            },
            title = {
                Text(
                    text = "ReadExternalStorage Permission",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            },
            text = {
                val text = if (isPermanentlyDeclined) {
                    "it seems like you permanently declined ReadExternalStorage permission go to setting and set permission."
                } else {
                    "ReadExternalStorage permission required to read sounds from storage."
                }
                Text(
                    text = text,
                    fontWeight = FontWeight.Normal,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = Color.Gray
                )
            }, icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Alert"
                )
            }
        )
    }
}

