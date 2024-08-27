package com.example.alarmko.feature_alarmClock.presentation.sounds.ExternalSound

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.alarmko.feature_alarmClock.data.Sounds.ExternalSounds
import com.example.alarmko.feature_alarmClock.domain.model.AudioItem
import com.example.alarmko.feature_alarmClock.domain.repository.SoundRepository
import com.example.alarmko.feature_alarmClock.presentation.util.Screens.ExternalSoundScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExternalSoundViewModel @Inject constructor(
    private val soundRepository: SoundRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var isLoading by mutableStateOf(true)

    private var _sounds = MutableStateFlow<List<AudioItem>>(emptyList())
    val sounds = _sounds.asStateFlow()
    private var _songUri = MutableStateFlow("")
    val songUri = _songUri.asStateFlow()

    private var _songName = MutableStateFlow("")
    val songName = _songName.asStateFlow()

    private var _searchState = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()



    init {
        _songUri.value = savedStateHandle.toRoute<ExternalSoundScreen>().songUri
        viewModelScope.launch {
            soundRepository.cacheDeviceSongs()

        }

        viewModelScope.launch {
            soundRepository.getDeviceSongs().collect{
                _sounds.value = it
                if(it.isNotEmpty()){
                    isLoading = false
                }
            }
        }


    }

    fun onSearchChange(text: String) {
        _searchState.value = searchState.value.copy(
            query = text
        )
//        if (searchState.value.query.isNotBlank()){
//            searchSounds.value = sounds.value.filter {
//                it.displayName.startsWith(searchState.value.query)
//            }
//
//        }
    }

    fun onSearchFocusStateChange(focusState: FocusState) {
        _searchState.value = searchState.value.copy(
            isHintVisible = !focusState.isFocused && searchState.value.query.isBlank()
        )
    }

    fun onSongChange(uri: String, name: String) {
        _songUri.value = uri
        _songName.value = name

    }


}