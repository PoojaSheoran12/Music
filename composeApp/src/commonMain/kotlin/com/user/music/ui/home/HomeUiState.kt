package com.user.music.ui.home

sealed class HomeUiState<out T> {
    object Loading : HomeUiState<Nothing>()
    data class Success<T>(val data: T) : HomeUiState<T>()
    data class Error(val message: String) : HomeUiState<Nothing>()
}