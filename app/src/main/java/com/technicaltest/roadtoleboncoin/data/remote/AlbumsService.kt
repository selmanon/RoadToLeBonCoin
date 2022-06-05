package com.technicaltest.roadtoleboncoin.data.remote

import com.technicaltest.roadtoleboncoin.data.AlbumEntity
import retrofit2.http.GET


interface AlbumsService {

    @GET("img/shared/technical-test.json")
    suspend fun fetchAlbums(): List<AlbumEntity>

    companion object {
        const val BASE_URL = "https://static.leboncoin.fr"
    }
}