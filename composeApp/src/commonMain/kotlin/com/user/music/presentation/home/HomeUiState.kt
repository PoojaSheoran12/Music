package com.user.music.presentation.home


enum class SortMode {
    NONE,
    NAME,
    DURATION
}
sealed interface HomeUiState {
    object Loading : HomeUiState
    object Idle : HomeUiState          // data shown, not loading
    data class Error(val message: String) : HomeUiState
}
