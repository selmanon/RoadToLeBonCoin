package com.technicaltest

import android.support.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.technicaltest.roadtoleboncoin.data.Album
import com.technicaltest.roadtoleboncoin.data.Result
import com.technicaltest.roadtoleboncoin.data.source.AlbumsRepository
import kotlinx.coroutines.runBlocking

class FakeRepository : AlbumsRepository{

    var albumsServiceData: LinkedHashMap<Int, Album> = LinkedHashMap()

    private var shouldReturnError = false

    private val observableAlbums = MutableLiveData<Result<List<Album>>>()


    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun observeAlbums(): LiveData<Result<List<Album>>> {
        runBlocking {
            observableAlbums.value = getAlbumse()
        }
        return observableAlbums
    }

    override suspend fun getAlbumse(): Result<List<Album>> {
        if (shouldReturnError) {
            return Result.Error(Exception("Test exception"))
        }
        return Result.Success(albumsServiceData.values.toList())    }


    override suspend fun saveAlbum(alum: Album) {
        albumsServiceData[alum.id] = alum }




    @VisibleForTesting
    fun addAlbums(vararg albums: Album) {
        for (album in albums) {
            albumsServiceData[album.id] = album
        }
        runBlocking {
            observableAlbums.value = getAlbums(true)
        }
    }

}
