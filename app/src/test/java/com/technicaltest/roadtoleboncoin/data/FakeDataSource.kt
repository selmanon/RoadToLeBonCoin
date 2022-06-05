package com.technicaltest.roadtoleboncoin.data

import androidx.lifecycle.LiveData
import com.technicaltest.roadtoleboncoin.data.source.AlbumsDataSource

class FakeDataSource(var albumEntities: MutableList<AlbumEntity>? = mutableListOf()) : AlbumsDataSource {
    override fun observeAlbums(): LiveData<Result<List<AlbumEntity>>> {
        TODO("Not yet implemented")
    }


    override suspend fun getAllAlbums(): Result<List<AlbumEntity>> {
        albumEntities?.let { return Result.Success(ArrayList(it)) }
        return Result.Error(
            Exception()
        )
    }


    override suspend fun saveAlbum(AlbumEntity: AlbumEntity) {
        albumEntities?.add(AlbumEntity)
    }

    override suspend fun deleteAllAlbums() {
        albumEntities?.clear()
    }

    override suspend fun saveAllAlbums(albumEntity: List<AlbumEntity>) {
        albumEntities?.addAll(albumEntity)
    }

}
