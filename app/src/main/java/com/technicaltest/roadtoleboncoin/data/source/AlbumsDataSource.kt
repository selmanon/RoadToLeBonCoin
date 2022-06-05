package com.technicaltest.roadtoleboncoin.data.source

import androidx.lifecycle.LiveData
import com.technicaltest.roadtoleboncoin.data.AlbumEntity
import com.technicaltest.roadtoleboncoin.data.*
/**
 * Interface to the data layer.
 */
interface AlbumsDataSource {
    fun observeAlbums(): LiveData<Result<List<AlbumEntity>>>

    suspend fun getAllAlbums(): Result<List<AlbumEntity>>

    suspend fun saveAlbum(alum: AlbumEntity)

    suspend fun deleteAllAlbums()

    suspend fun saveAllAlbums(albumEntities : List<AlbumEntity>)

}