package com.user.music.ui.home


enum class SortMode {
    NONE,
    NAME,
    DURATION
}
data class HomeUiState(
    val isInitialLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val errorMessage: String? = null
)
