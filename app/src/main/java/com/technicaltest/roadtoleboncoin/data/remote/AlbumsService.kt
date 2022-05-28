package com.technicaltest.roadtoleboncoin.data.remote

import com.technicaltest.roadtoleboncoin.data.Album
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface AlbumsService {

    @GET("img/shared/technical-test.json")
    suspend fun fetchAlbums(): List<Album>

    companion object {
        const val BASE_URL = "https://static.leboncoin.fr"
    }
}