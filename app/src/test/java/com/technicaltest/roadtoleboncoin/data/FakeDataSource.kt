package com.technicaltest.roadtoleboncoin.data

import androidx.lifecycle.LiveData
import com.technicaltest.roadtoleboncoin.data.source.AlbumsDataSource

class FakeDataSource(var albums: MutableList<Album>? = mutableListOf()) : AlbumsDataSource {
    override fun observeAlbums(): LiveData<Result<List<Album>>> {
        TODO("Not yet implemented")
    }


    override suspend fun getAllAlbums(): Result<List<Album>> {
        albums?.let { return Result.Success(ArrayList(it)) }
        return Result.Error(
            Exception()
        )
    }


    override suspend fun saveAlbum(Album: Album) {
        albums?.add(Album)
    }

    override suspend fun deleteAllAlbums() {
        albums?.clear()
    }

    override suspend fun saveAllAlbums(album: List<Album>) {
        albums?.addAll(album)
    }

}
