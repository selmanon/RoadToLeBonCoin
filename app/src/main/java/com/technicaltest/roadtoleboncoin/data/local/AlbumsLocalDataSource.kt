package com.technicaltest.roadtoleboncoin.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.technicaltest.roadtoleboncoin.data.AlbumEntity
import com.technicaltest.roadtoleboncoin.data.source.AlbumsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import com.technicaltest.roadtoleboncoin.data.*
import javax.inject.Inject

/**
 * Concrete implementation of a local data source as a db.
 */

class AlbumsLocalDataSource @Inject internal constructor(private val albumsDao: AlbumsDao, private val ioDispatcher : CoroutineDispatcher) : AlbumsDataSource{
    override fun observeAlbums(): LiveData<Result<List<AlbumEntity>>> {
        return Transformations.map(albumsDao.observeAlbums()) {
            Result.Success(it)
        }
    }

    override suspend fun getAllAlbums(): Result<List<AlbumEntity>> {
        return try {
            Result.Success(albumsDao.getAllAlbums())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveAlbum(alum: AlbumEntity) {
        albumsDao.insertAlbum(alum)
    }

    override suspend fun deleteAllAlbums() {
        albumsDao.deleteAlbums()
    }

    override suspend fun saveAllAlbums(albumEntities : List<AlbumEntity>) {
        albumsDao.insertAllAlbums(albumEntities)
    }

}