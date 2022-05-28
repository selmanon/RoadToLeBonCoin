package com.technicaltest

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.technicaltest.roadtoleboncoin.data.Album
import com.technicaltest.roadtoleboncoin.data.Result
import com.technicaltest.roadtoleboncoin.data.source.AlbumsDataSource

object FakeFailingAlbumsRemoteDataSource : AlbumsDataSource {

    override fun observeAlbums(): LiveData<Result<List<Album>>> {
        return liveData { emit(getAllAlbums()) }
    }

    override suspend fun getAllAlbums(): Result<List<Album>> {
        return Result.Error(Exception("Test"))
    }

    override suspend fun saveAlbum(alum: Album) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllAlbums() {
        TODO("Not yet implemented")
    }

    override suspend fun saveAllAlbums(albums: List<Album>) {
        TODO("Not yet implemented")
    }
}