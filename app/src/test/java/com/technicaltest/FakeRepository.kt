package com.technicaltest

import android.support.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.technicaltest.roadtoleboncoin.data.AlbumEntity
import com.technicaltest.roadtoleboncoin.data.Result
import com.technicaltest.roadtoleboncoin.data.source.AlbumsRepository
import com.technicaltest.roadtoleboncoin.domain.model.Album
import kotlinx.coroutines.runBlocking

class FakeRepository : AlbumsRepository{

    var albumsServiceData: LinkedHashMap<Int, Album> = LinkedHashMap()

    private var shouldReturnError = false

    private val observableAlbums = MutableLiveData<Result<List<Album>>>()


    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override fun observeAlbums(): LiveData<Result<List<Album>>> {
        runBlocking {
            observableAlbums.value = getAlbums()
        }
        return observableAlbums
    }

    override suspend fun getAlbums(): Result<List<Album>> {
        if (shouldReturnError) {
            return Result.Error(Exception("Test exception"))
        }
        return Result.Success(albumsServiceData.values.toList())    }


    override suspend fun saveAlbum(alum: AlbumEntity) {
        albumsServiceData[alum.id] = Album(alum.title?:"", alum.url?:"", alum.thumbnailUrl?:"") }




    @VisibleForTesting
    fun addAlbums(vararg albumEntities: AlbumEntity) {
        for (album in albumEntities) {
            albumsServiceData[album.id] = Album(album.title?:"", album.url?:"", album.thumbnailUrl?:"")
        }
        runBlocking {
            observableAlbums.value = getAlbums()
        }
    }

}
