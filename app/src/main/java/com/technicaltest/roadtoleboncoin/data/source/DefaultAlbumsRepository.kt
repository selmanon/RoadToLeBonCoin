package com.technicaltest.roadtoleboncoin.data.source

import androidx.lifecycle.LiveData
import com.technicaltest.roadtoleboncoin.data.Album
import com.technicaltest.roadtoleboncoin.data.local.AlbumsLocalDataSource
import com.technicaltest.roadtoleboncoin.data.remote.AlbumsRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import com.technicaltest.roadtoleboncoin.data.*
import java.lang.Exception

class DefaultAlbumsRepository(
    private val albumsRemoteDataSource: AlbumsDataSource,
    private val albumsLocalDataSource: AlbumsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AlbumsRepository{

    override suspend fun getAlbums(forceUpdate: Boolean): Result<List<Album>> {
        if(forceUpdate) {
            try {
                updateAlbumsFromRemoteDataSource()
            } catch ( e : Exception) {
                return Result.Error(e)
            }
        }

        return albumsLocalDataSource.getAllAlbums()
    }


    override fun observeAlbums(): LiveData<Result<List<Album>>> {
        TODO("Not yet implemented")
    }



    override suspend fun saveAlbum(alum: Album) {
        albumsLocalDataSource.saveAlbum(alum)
    }


    private suspend fun updateAlbumsFromRemoteDataSource() {
        val remoteAlbums = albumsRemoteDataSource.getAllAlbums()

        if (remoteAlbums is Result.Success) {
            // Real apps might want to do a proper sync, deleting, modifying or adding each album.
            albumsLocalDataSource.deleteAllAlbums()
            remoteAlbums.data.forEach { album ->
                albumsLocalDataSource.saveAlbum(album)
            }
        } else if (remoteAlbums is Result.Error) {
            throw remoteAlbums.exception
        }
    }

}