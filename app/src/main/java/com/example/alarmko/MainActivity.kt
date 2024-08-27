package com.example.alarmko

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.example.alarmko.feature_alarmClock.domain.alarmService.AlarmMediaPlayer
import com.example.alarmko.feature_alarmClock.domain.alarmService.AlarmService
import com.example.alarmko.feature_alarmClock.domain.alarmService.SetAlarmMediaPlayer
import com.example.alarmko.feature_alarmClock.domain.workers.InitializeRepeatingAlarmWorker
import com.example.alarmko.feature_alarmClock.presentation.MainAlarm.components.RootMainAlarmScreen
import com.example.alarmko.feature_alarmClock.presentation.addEditAlarm.components.RootAddEditAlarmScreen
import com.example.alarmko.feature_alarmClock.presentation.sounds.ExternalSound.component.ReadAudioPermission
import com.example.alarmko.feature_alarmClock.presentation.sounds.ExternalSound.component.RootExternalSongScreen
import com.example.alarmko.feature_alarmClock.presentation.sounds.RingTone.Component.RootRingToneSoundScreen
import com.example.alarmko.feature_alarmClock.presentation.util.Screens.AddEditAlarmScreen
import com.example.alarmko.feature_alarmClock.presentation.util.Screens.ExternalSoundScreen
import com.example.alarmko.feature_alarmClock.presentation.util.Screens.MainAlarmScreen
import com.example.alarmko.feature_alarmClock.presentation.util.Screens.RingToneSongScreen
import com.example.compose.AlarmKoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var alarmMediaPlayer: SetAlarmMediaPlayer



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val visiblePermissionDialogQueue = remember {
                mutableStateListOf<String>()
            }

            val readAudioPerm = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val granted = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_AUDIO
                ) == PackageManager.PERMISSION_GRANTED
                Pair(Manifest.permission.READ_MEDIA_AUDIO, granted)

            } else {
                val granted = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                Pair(Manifest.permission.READ_EXTERNAL_STORAGE, granted)
            }
            var readAudioPermIsGranted by remember {
                mutableStateOf(readAudioPerm.second)
            }
            val readAudioPermLauncher =
                rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        if (!isGranted && !visiblePermissionDialogQueue.contains(readAudioPerm.first))
                            visiblePermissionDialogQueue.add(readAudioPerm.first)
                        readAudioPermIsGranted = isGranted
                    }
                )


            val navController = rememberNavController()



            AlarmKoTheme() {

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->
                    NavHost(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController,
                        startDestination = MainAlarmScreen
                    ) {
                        composable<MainAlarmScreen> {

                            RootMainAlarmScreen(
                                onAlarmItemClick = { id ->
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "alarmId",
                                        id
                                    )
                                    navController.navigate(AddEditAlarmScreen(id.toString()))
                                },
                                onInsertAlarmItem = {
                                    navController.navigate(AddEditAlarmScreen(null))
                                }
                            )

                        }
                        composable<AddEditAlarmScreen> {
                            //get from next screen
                            val songUri = it.savedStateHandle.get<String>("songUri")
                            val songName = it.savedStateHandle.get<String>("songName")

                            RootAddEditAlarmScreen(
                                navController = navController,
                                songUri = songUri,
                                songName = songName
                            )
                        }
                        composable<RingToneSongScreen> {
                            val songUri = it.savedStateHandle.get<String>("songUri")
                            val songName = it.savedStateHandle.get<String>("songName")

                            RootRingToneSoundScreen(
                                navController = navController,
                                songUri = songUri,
                                songName = songName,
                                onStopMediaPlayer = {
                                    alarmMediaPlayer.stopMediaPlayer()
                                },
                                onSetMediaPlayer = { uri ->
                                    lifecycleScope.launch { alarmMediaPlayer.playMediaPlayer(uri) }
                                })
                        }
                        composable<ExternalSoundScreen> {

                            if (readAudioPermIsGranted) {

                                RootExternalSongScreen(
                                    navController = navController,
                                    onStopMediaPlayer = {
                                        alarmMediaPlayer.stopMediaPlayer()
                                    },
                                    onSetMediaPlayer = { uri ->
                                        lifecycleScope.launch {

                                            alarmMediaPlayer.playMediaPlayer(uri)
                                        }
                                    })
                            } else {
                                LaunchedEffect(true) {
                                    readAudioPermLauncher.launch(readAudioPerm.first)
                                }
                                visiblePermissionDialogQueue.forEach {
                                    if (visiblePermissionDialogQueue.contains(it)) {
                                        ReadAudioPermission(
                                            navController = navController,
                                            permissionQueueState = visiblePermissionDialogQueue,
                                            readAudioPerm = readAudioPerm,
                                            onOpenSetting = {
                                                openSetting()
                                            },
                                            activityResult = readAudioPermLauncher
                                        )
                                    }
                                }
                            }


                        }

                    }

                }

            }
        }
    }

    private fun openSetting() {

        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        ).also {
            startActivity(it)
        }
    }
}

