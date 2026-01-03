package com.user.music.ui.home

import com.user.music.data.repository.HomeRepository
import com.user.music.domain.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

class HomeViewModel(
    private val repository: HomeRepository,
    private val scope: CoroutineScope
) {

    private val _uiState =
        MutableStateFlow<HomeUiState<List<Track>>>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState<List<Track>>> = _uiState.asStateFlow()

    private var cachedTracks: List<Track> = emptyList()

    fun loadTracks() {
        scope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                cachedTracks = repository.getTracks()
                _uiState.value = HomeUiState.Success(cachedTracks)
            } catch (e: IOException) {
                _uiState.value = HomeUiState.Error("No internet connection")
            } catch (e: SerializationException) {
                _uiState.value = HomeUiState.Error("Invalid server response")
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Something went wrong")
            }
        }
    }
    fun play(track: Track){

    }

    fun sortByName() {
        _uiState.value =
            HomeUiState.Success(cachedTracks.sortedBy { it.title.lowercase() })
    }

    fun sortByDuration() {
        _uiState.value =
            HomeUiState.Success(cachedTracks.sortedBy { it.durationSec })
    }

    fun clear() {
        scope.cancel()
    }
}