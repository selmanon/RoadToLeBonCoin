package com.technicaltest.roadtoleboncoin.presentation

import androidx.lifecycle.*
import com.technicaltest.roadtoleboncoin.data.AlbumEntity
import com.technicaltest.roadtoleboncoin.data.Result
import com.technicaltest.roadtoleboncoin.data.source.AlbumsRepository
import com.technicaltest.roadtoleboncoin.domain.model.Album
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(private val repository: AlbumsRepository) : ViewModel() {


     val albumUiState: MutableLiveData<UiState<List<Album>>> = MutableLiveData()

    private val _albums: LiveData<Result<List<Album>>> = loadAlbums()

    val albums: LiveData<List<Album>> = Transformations.map(_albums) {
        if (it is Result.Success) {
            it.data
        } else {
            null
        }
    }

    init {
        // Set initial state
        loadAlbums()
    }


    fun loadAlbums(): LiveData<Result<List<Album>>> {
        albumUiState.postValue(UiState.Loading)

        viewModelScope.launch {
            repository.getAlbums()
            albumUiState.postValue(UiState.FinishLoading)
        }
         return repository.observeAlbums()
    }

}