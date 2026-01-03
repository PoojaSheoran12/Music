package com.user.music.ui.home


import com.user.music.domain.model.Track
import com.user.music.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*


class HomeViewModel(
    private val repository: HomeRepository,
    private val scope: CoroutineScope
) {

    private val sortMode =
        MutableStateFlow(SortMode.NONE)


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
