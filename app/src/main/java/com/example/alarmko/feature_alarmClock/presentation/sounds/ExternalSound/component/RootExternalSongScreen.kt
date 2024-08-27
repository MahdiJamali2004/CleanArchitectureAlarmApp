package com.example.alarmko.feature_alarmClock.presentation.sounds.ExternalSound.component

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.alarmko.feature_alarmClock.presentation.sounds.ExternalSound.ExternalSoundViewModel
import com.example.compose.cinzelFontFamily


@Composable
fun RootExternalSongScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onSetMediaPlayer: (String) -> Unit,
    externalSoundViewModel: ExternalSoundViewModel = hiltViewModel(),
    onStopMediaPlayer: () -> Unit

) {

    val sounds by externalSoundViewModel.sounds.collectAsState()

    if (sounds.isNotEmpty()){
        externalSoundViewModel.isLoading = false
    }

    BackHandler {
        val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
        savedStateHandle?.set(
            "songUri",
            externalSoundViewModel.songUri.value
        )
        savedStateHandle?.set(
            "songName",
            externalSoundViewModel.songName.value
        )
        onStopMediaPlayer()
        navController.popBackStack()
    }
    if (externalSoundViewModel.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize() ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Loading...",
                    fontWeight = FontWeight.Bold,
                    fontFamily = cinzelFontFamily,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
            }
        }
    } else {
        ExternalSongScreen(
            modifier = modifier,
            audioItems = sounds,
            songUri = externalSoundViewModel.songUri.collectAsState().value,
            searchState = externalSoundViewModel.searchState.collectAsState().value,
            onBackPress = {
                val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
                savedStateHandle?.set(
                    "songUri",
                    externalSoundViewModel.songUri.value
                )
                savedStateHandle?.set(
                    "songName",
                    externalSoundViewModel.songName.value
                )
                onStopMediaPlayer()
                navController.popBackStack()

            },
            searchQueryChange = {
                externalSoundViewModel.onSearchChange(it)
            },
            onSelectedUriChange = { uri, name ->
                externalSoundViewModel.onSongChange(uri, name)
                onSetMediaPlayer(uri)
            },
            searchFocusChange = {
                externalSoundViewModel.onSearchFocusStateChange(it)
            }
        )
    }

}

