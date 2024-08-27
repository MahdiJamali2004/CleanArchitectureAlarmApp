package com.example.alarmko.feature_alarmClock.presentation.sounds.RingTone

import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.alarmko.feature_alarmClock.data.Sounds.RingtoneSounds
import com.example.alarmko.feature_alarmClock.domain.model.AudioItem
import com.example.alarmko.feature_alarmClock.domain.model.RingtoneItem
import com.example.alarmko.feature_alarmClock.domain.repository.SoundRepository
import com.example.alarmko.feature_alarmClock.presentation.util.Screens.RingToneSongScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RingToneSoundsViewModel @Inject constructor(
    private val soundRepository: SoundRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _ringTones = MutableStateFlow<List<RingtoneItem>>(emptyList())
    val ringTones = _ringTones.asStateFlow()

    private var _songUri = MutableStateFlow("")
    val songUri = _songUri.asStateFlow()

    private var _songName = MutableStateFlow("")
    val songName = _songName.asStateFlow()

    init {

        _songUri.value = savedStateHandle.toRoute<RingToneSongScreen>().songUri
        _ringTones.value = soundRepository.getAlarmSongs()

    }

    fun onSongChange(uri: String,name :String) {
        _songUri.value = uri
        _songName.value = name

    }

}