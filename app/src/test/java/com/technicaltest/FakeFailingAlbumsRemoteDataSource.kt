package com.technicaltest

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.technicaltest.roadtoleboncoin.data.AlbumEntity
import com.technicaltest.roadtoleboncoin.data.Result
import com.technicaltest.roadtoleboncoin.data.source.AlbumsDataSource

object FakeFailingAlbumsRemoteDataSource : AlbumsDataSource {

    override fun observeAlbums(): LiveData<Result<List<AlbumEntity>>> {
        return liveData { emit(getAllAlbums()) }
    }

    override suspend fun getAllAlbums(): Result<List<AlbumEntity>> {
        return Result.Error(Exception("Test"))
    }

    override suspend fun saveAlbum(alum: AlbumEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllAlbums() {
        TODO("Not yet implemented")
    }

    override suspend fun saveAllAlbums(albumEntities: List<AlbumEntity>) {
        TODO("Not yet implemented")
    }
}