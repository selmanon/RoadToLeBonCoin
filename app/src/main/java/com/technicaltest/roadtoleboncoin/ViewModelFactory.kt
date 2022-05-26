package com.technicaltest.roadtoleboncoin

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.technicaltest.roadtoleboncoin.albums.AlbumsViewModel
import com.technicaltest.roadtoleboncoin.data.source.AlbumsRepository

/**
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory (
    private val albumsRepository: AlbumsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumsViewModel::class.java)) {
            return AlbumsViewModel(albumsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")    }

}