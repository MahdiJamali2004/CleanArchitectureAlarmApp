package com.example.alarmko.feature_alarmClock.presentation.sounds.RingTone.Component

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.alarmko.feature_alarmClock.presentation.sounds.RingTone.RingToneSoundsViewModel
import com.example.alarmko.feature_alarmClock.presentation.util.Screens.ExternalSoundScreen

@Composable
fun RootRingToneSoundScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    songUri : String?,
    songName : String?,
    onSetMediaPlayer: (String) -> Unit,
    onStopMediaPlayer : () -> Unit,
    ringToneSoundsViewModel: RingToneSoundsViewModel = hiltViewModel()
) {

    LaunchedEffect(true) {
        if (songUri != null && songName != null) {
            ringToneSoundsViewModel.onSongChange(songUri, songName)
        }
    }


    BackHandler {
        val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
        savedStateHandle?.set(
            "songUri",
            ringToneSoundsViewModel.songUri.value
        )
        savedStateHandle?.set(
            "songName",
            ringToneSoundsViewModel.songName.value
        )
        navController.popBackStack()
        onStopMediaPlayer()

    }
    RingToneSoundScreen(
        modifier = modifier,
        songs = ringToneSoundsViewModel.ringTones.collectAsState().value,
        selectedUri = ringToneSoundsViewModel.songUri.collectAsState().value,
        onBackPress = {
            val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
            savedStateHandle?.set(
                "songUri",
                ringToneSoundsViewModel.songUri.value
            )
            savedStateHandle?.set(
                "songName",
                ringToneSoundsViewModel.songName.value
            )

            navController.popBackStack()
            onStopMediaPlayer()
        },
        onSelectedUriChange = { uri, name ->
            ringToneSoundsViewModel.onSongChange(uri, name)
            onSetMediaPlayer(uri)
        },
        onExternalSounds = {
            navController.navigate(ExternalSoundScreen(ringToneSoundsViewModel.songUri.value))
            onStopMediaPlayer()
        }
    )

}