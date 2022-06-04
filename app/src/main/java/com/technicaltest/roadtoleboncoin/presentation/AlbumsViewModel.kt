package com.technicaltest.roadtoleboncoin.presentation

import androidx.lifecycle.*
import com.technicaltest.roadtoleboncoin.data.Album
import com.technicaltest.roadtoleboncoin.data.Result
import com.technicaltest.roadtoleboncoin.data.source.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(private val repository: AlbumsRepository) : ViewModel() {


     val albumUiState: MutableLiveData<UiState> = MutableLiveData()

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

        viewModelScope.launch {
            repository.getAlbums()
            albumUiState.postValue(UiState.FinishLoading)
        }
         return repository.observeAlbums()
    }

}