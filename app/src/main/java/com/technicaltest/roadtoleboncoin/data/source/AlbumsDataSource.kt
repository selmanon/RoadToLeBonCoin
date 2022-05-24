package com.technicaltest.roadtoleboncoin.data.source

import androidx.lifecycle.LiveData
import com.technicaltest.roadtoleboncoin.data.Album
import com.technicaltest.roadtoleboncoin.data.*
/**
 * Interface to the data layer.
 */
interface AlbumsDataSource {
    fun observeAlbums(): LiveData<Result<List<Album>>>

    suspend fun getAllAlbums(): Result<List<Album>>

    suspend fun saveAlbum(alum: Album)

    suspend fun deleteAllAlbums()

}