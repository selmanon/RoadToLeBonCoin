package com.technicaltest.roadtoleboncoin.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.technicaltest.roadtoleboncoin.data.Album
import com.technicaltest.roadtoleboncoin.data.local.AlbumsLocalDataSource
import com.technicaltest.roadtoleboncoin.data.remote.AlbumsRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import com.technicaltest.roadtoleboncoin.data.*
import java.lang.Exception
import javax.inject.Inject


class DefaultAlbumsRepository @Inject constructor(
    val albumsRemoteDataSource: AlbumsDataSource,
    val albumsLocalDataSource: AlbumsDataSource,
    private val ioDispatcher: CoroutineDispatcher
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


    override fun observeAlbums(): LiveData<Result<List<Album>>> = albumsLocalDataSource.observeAlbums()


    override suspend fun saveAlbum(alum: Album) {
        albumsLocalDataSource.saveAlbum(alum)
    }


    private suspend fun updateAlbumsFromRemoteDataSource() {
        val remoteAlbums = albumsRemoteDataSource.getAllAlbums()

        if (remoteAlbums is Result.Success) {
            // Real apps might want to do a proper sync, deleting, modifying or adding each album.
            albumsLocalDataSource.deleteAllAlbums()
            albumsLocalDataSource.saveAllAlbums(remoteAlbums.data)
        } else if (remoteAlbums is Result.Error) {
            throw remoteAlbums.exception
        }
    }

}