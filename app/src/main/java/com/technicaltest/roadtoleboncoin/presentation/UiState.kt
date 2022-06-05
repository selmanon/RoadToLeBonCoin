package com.technicaltest.roadtoleboncoin.presentation

import com.technicaltest.roadtoleboncoin.domain.model.Album

sealed class UiState<out R> {
    object Empty : UiState<Nothing>()
    object FinishLoading : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Data<T>(val data: List<Album>) : UiState<T>()
    data class Error<T>(val errorMsg: String) : UiState<Nothing>()

}