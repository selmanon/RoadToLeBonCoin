package com.technicaltest.roadtoleboncoin.presentation

import com.technicaltest.roadtoleboncoin.data.Album

sealed class UiState {
    object Empty : UiState()
    object FinishLoading : UiState()
    object Loading : UiState()
    class Data(data: List<Album>) : UiState()
    class Error(errorMsg: String) : UiState(
    )
}