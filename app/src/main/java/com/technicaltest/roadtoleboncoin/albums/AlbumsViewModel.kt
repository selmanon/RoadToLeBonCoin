package com.technicaltest.roadtoleboncoin.albums

import androidx.lifecycle.*
import com.technicaltest.roadtoleboncoin.data.Album
import com.technicaltest.roadtoleboncoin.data.Result
import com.technicaltest.roadtoleboncoin.data.source.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(private val repository: AlbumsRepository) : ViewModel() {

    private val _forceUpdate = MutableLiveData(false)

    private val _albums: LiveData<Result<List<Album>>> = Transformations.switchMap(_forceUpdate) { forceUpdate ->
        if (forceUpdate) {
            _dataLoading.value = true
            viewModelScope.launch {
                repository.getAlbums(forceUpdate)
                _dataLoading.value = false
            }
        }

     repository.observeAlbums()
    }

    val error: LiveData<Boolean> = Transformations.map(_albums) { it is Error }
    val empty: LiveData<Boolean> = Transformations.map(_albums) { (it as? Result.Success)?.data.isNullOrEmpty() }



    val albums: LiveData<List<Album>> = Transformations.map(_albums) {
        if (it is Result.Success) {
            it.data
        } else {
            null
        }
    }

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    init {
        // Set initial state
        loadAlbums(true)
    }

    /**
     * @param forceUpdate Pass in true to refresh the data in the [AlbumsDataSource]
     */
    fun loadAlbums(forceUpdate: Boolean) {
        _forceUpdate.value = forceUpdate
    }


    fun refresh() {
        _forceUpdate.value = true
    }
}