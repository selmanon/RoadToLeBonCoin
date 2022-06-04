package com.technicaltest.roadtoleboncoin.data.source

import androidx.lifecycle.LiveData
import com.technicaltest.roadtoleboncoin.data.Album
import com.technicaltest.roadtoleboncoin.data.Result
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class DefaultAlbumsRepository @Inject constructor(
    val albumsRemoteDataSource: AlbumsDataSource,
    val albumsLocalDataSource: AlbumsDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : AlbumsRepository{



    override fun observeAlbums(): LiveData<Result<List<Album>>> = albumsLocalDataSource.observeAlbums()
    override suspend fun getAlbums(): Result<List<Album>> {
        try {
            updateAlbumsFromRemoteDataSource()
        } catch ( e : Exception) {
            return Result.Error(e)
        }


        return albumsLocalDataSource.getAllAlbums()    }


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