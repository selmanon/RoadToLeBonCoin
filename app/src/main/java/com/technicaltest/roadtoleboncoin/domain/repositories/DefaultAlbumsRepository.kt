package com.technicaltest.roadtoleboncoin.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.technicaltest.roadtoleboncoin.data.AlbumEntity
import com.technicaltest.roadtoleboncoin.data.Result
import com.technicaltest.roadtoleboncoin.data.source.AlbumsDataSource
import com.technicaltest.roadtoleboncoin.data.source.AlbumsRepository
import com.technicaltest.roadtoleboncoin.domain.model.Album
import com.technicaltest.roadtoleboncoin.domain.mappers.AlbumMapper
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class DefaultAlbumsRepository @Inject constructor(
    val albumsRemoteDataSource: AlbumsDataSource,
    val albumsLocalDataSource: AlbumsDataSource,
    private val ioDispatcher: CoroutineDispatcher,
    val albumMapper: AlbumMapper
) : AlbumsRepository {


    override fun observeAlbums(): LiveData<Result<List<Album>>> {
        val albums = mutableListOf<Album>()
        val result = albumsLocalDataSource.observeAlbums().value
        if (result is Result.Success) {
            for (albumEntity in result.data) {
                albums.add(albumMapper.mapAlbumEntityToDomain(albumEntity))
            }
        }

        return liveData { Result.Success(albums) }
    }

    override suspend fun getAlbums(): Result<List<Album>> {
        try {
            updateAlbumsFromRemoteDataSource()
        } catch (e: Exception) {
            return Result.Error(e)
        }

        val result = albumsLocalDataSource.getAllAlbums()

        val albums = mutableListOf<Album>()

        return when (result) {
            is Result.Success -> {
                result.data.forEach { albumEntity ->
                    albums.add(albumMapper.mapAlbumEntityToDomain(albumEntity))
                }

                return Result.Success(albums)
            }

            is Result.Error -> {
                return Result.Error(result.exception)
            }

            is Result.Empty -> {
                return Result.Empty
            }
            else -> {
                return Result.Loading
            }
        }

    }


    override suspend fun saveAlbum(alum: AlbumEntity) {
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