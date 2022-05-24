package com.technicaltest.roadtoleboncoin.data.remote

import androidx.lifecycle.LiveData
import com.technicaltest.roadtoleboncoin.data.Album
import com.technicaltest.roadtoleboncoin.data.source.AlbumsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import com.technicaltest.roadtoleboncoin.data.*

/**
 * Concrete implementation of a local data source as a db.
 */

class AlbumsRemoteDataSource internal constructor(private val retrofit: Retrofit, private val ioDispatcher : CoroutineDispatcher) : AlbumsDataSource{

    override fun observeAlbums(): LiveData<Result<List<Album>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllAlbums(): Result<List<Album>> {
        val albumsService = retrofit.create(AlbumsService::class.java);

        val albums = withContext(IO) {
            albumsService.fetchAlbums()
        }

        return Result.Success(albums)
    }

    override suspend fun saveAlbum(alum: Album) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllAlbums() {
        TODO("Not yet implemented")
    }
}



