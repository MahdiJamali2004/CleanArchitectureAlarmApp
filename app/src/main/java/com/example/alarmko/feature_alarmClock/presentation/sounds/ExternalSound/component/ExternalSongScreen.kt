package com.example.alarmko.feature_alarmClock.presentation.sounds.ExternalSound.component

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.alarmko.feature_alarmClock.domain.model.AudioItem
import com.example.alarmko.feature_alarmClock.presentation.sounds.ExternalSound.SearchState
import com.example.compose.cinzelFontFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExternalSongScreen(
    modifier: Modifier = Modifier,
    audioItems: List<AudioItem>,
    songUri: String,
    searchState: SearchState,
    onBackPress: () -> Unit,
    searchQueryChange: (String) -> Unit,
    onSelectedUriChange: (uri: String, name: String) -> Unit,
    searchFocusChange: (FocusState) -> Unit
) {
    var filteredAudioItems by remember {
        mutableStateOf(audioItems)
    }
    filteredAudioItems = if (searchState.query.isNotBlank()) {
        audioItems.filter { it.displayName.lowercase().contains(searchState.query) }
    } else {
        audioItems
    }
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
                text = "Music on device",
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
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            searchFocusChange(it)
                        },
                    value = if (searchState.isHintVisible) searchState.hint else searchState.query,
                    onValueChange = searchQueryChange,
                    singleLine = true,
                    textStyle = TextStyle(
                        color = if (searchState.isHintVisible) Color.Gray else MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        fontFamily = cinzelFontFamily,
                    )
                )

            }


        }


        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            itemsIndexed(filteredAudioItems) { index, audioItem ->
                ExternalSongItem(
                    audioItem = audioItem,
                    checked = audioItem.uri == songUri,
                    onItemClick = {
                        onSelectedUriChange(audioItem.uri, audioItem.displayName)
                    }
                )
                if (audioItems.lastIndex != index)
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }

        }

    }

}