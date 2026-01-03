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

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
enum class SortMode {
    NONE,
    NAME,
    DURATION
}

class HomeViewModel(
    private val repository: HomeRepository,
    private val scope: CoroutineScope
) {

    // Sorting mode
    private val sortMode =
        MutableStateFlow<SortMode>(SortMode.NONE)

    // Tracks coming from DB
    val tracks: StateFlow<List<Track>> =
        combine(
            repository.observeTracks(),
            sortMode
        ) { tracks, mode ->
            when (mode) {
                SortMode.NONE -> tracks
                SortMode.NAME ->
                    tracks.sortedBy { it.title.lowercase() }
                SortMode.DURATION ->
                    tracks.sortedBy { it.durationSec }
            }
        }.stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    init {
        // Initial page load
        scope.launch {
            repository.loadNextPage()
        }
    }

    fun loadMore() {
        scope.launch {
            repository.loadNextPage()
        }
    }

    fun refresh() {
        scope.launch {
            repository.refresh()
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

    fun clear() {
        scope.cancel()
    }
}
