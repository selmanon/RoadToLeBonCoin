package com.technicaltest.roadtoleboncoin.data.remote

import androidx.lifecycle.LiveData
import com.technicaltest.roadtoleboncoin.data.AlbumEntity
import com.technicaltest.roadtoleboncoin.data.source.AlbumsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.technicaltest.roadtoleboncoin.data.*
import javax.inject.Inject

/**
 * Concrete implementation of a a remote Data source.
 */

class AlbumsRemoteDataSource @Inject internal constructor(private val albumsService: AlbumsService, private val ioDispatcher : CoroutineDispatcher) : AlbumsDataSource{

    override fun observeAlbums(): LiveData<Result<List<AlbumEntity>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllAlbums(): Result<List<AlbumEntity>> {
        val albums = withContext(ioDispatcher) {
            albumsService.fetchAlbums()
        }

        return Result.Success(albums)
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



