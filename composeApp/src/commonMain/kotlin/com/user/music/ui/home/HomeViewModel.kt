package com.user.music.ui.home


import com.user.music.domain.model.Track
import com.user.music.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*
import kotlinx.io.IOException


class HomeViewModel(
    private val repository: HomeRepository,
    private val scope: CoroutineScope
) {

    private val sortMode = MutableStateFlow(SortMode.NONE)

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    val tracks: StateFlow<List<Track>> =
        combine(
            repository.observeTracks(),
            sortMode
        ) { tracks, mode ->
            when (mode) {
                SortMode.NONE -> tracks
                SortMode.NAME -> tracks.sortedBy { it.title.lowercase() }
                SortMode.DURATION -> tracks.sortedBy { it.durationSec }
            }
        }.stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    init {
        loadInitial()
    }


    private fun loadInitial() {
        scope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                repository.loadNextPage()
                _uiState.value = HomeUiState.Idle
            } catch (e: IOException) {
                _uiState.value = HomeUiState.Error("No internet connection")
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Something went wrong")
            }
        }
    }


    fun loadMore() {

        if (_uiState.value == HomeUiState.Loading) return

        scope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                repository.loadNextPage()
                _uiState.value = HomeUiState.Idle
            } catch (e: IOException) {
                _uiState.value = HomeUiState.Error("Network error while loading more")
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Failed to load more tracks")
            }
        }
    }

    // 🔹 Pull-to-refresh
    fun refresh() {
        scope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                repository.refresh()
                _uiState.value = HomeUiState.Idle
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Failed to refresh")
            }
        }
    }


    fun sortByName() {
        sortMode.value = SortMode.NAME
    }

    fun sortByDuration() {
        sortMode.value = SortMode.DURATION
    }

    fun clearSort() {
        sortMode.value = SortMode.NONE
    }


    fun retry() {
        loadInitial()
    }

    fun clear() {
        scope.cancel()
    }
}
