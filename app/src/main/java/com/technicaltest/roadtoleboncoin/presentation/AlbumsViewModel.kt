package com.technicaltest.roadtoleboncoin.presentation

import androidx.lifecycle.*
import com.technicaltest.roadtoleboncoin.data.Result
import com.technicaltest.roadtoleboncoin.domain.model.Album
import com.technicaltest.roadtoleboncoin.domain.usecase.GetAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(val getAlbumsUseCase: GetAlbumsUseCase) : ViewModel() {

    val albumUiState: MutableLiveData<UiState<List<Album>>> = MutableLiveData()

    init {
        loadAlbums()
    }

    fun loadAlbums() {
        albumUiState.postValue(UiState.Loading)

        viewModelScope.launch {
            albumUiState.postValue(UiState.FinishLoading)

            when(val albumsResult = getAlbumsUseCase.invoke()) {
                is Result.Success -> {
                    albumUiState.postValue(UiState.Success(albumsResult.data))

                }
                is Result.Empty -> {
                    albumUiState.postValue(UiState.Empty)

                }

                is Result.Error -> {
                    albumUiState.postValue(UiState.Error(albumsResult.toString()))
                }
                else -> {}
            }



        }
    }


}