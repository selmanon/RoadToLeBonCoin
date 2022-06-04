package com.technicaltest.roadtoleboncoin.data.source

import androidx.lifecycle.LiveData
import com.technicaltest.roadtoleboncoin.data.Album
import com.technicaltest.roadtoleboncoin.data.*
/**
 * Main entry point for accessing albums data.
 */
interface AlbumsRepository {
    suspend fun observeAlbums(): LiveData<Result<List<Album>>>

    suspend fun getAlbumse(): Result<List<Album>>

    suspend fun saveAlbum(alum: Album)
}