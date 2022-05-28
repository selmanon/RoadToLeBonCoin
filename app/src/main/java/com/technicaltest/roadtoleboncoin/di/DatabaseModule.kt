package com.technicaltest.roadtoleboncoin.di

import android.app.Application
import android.content.Context
import com.google.gson.GsonBuilder
import com.technicaltest.roadtoleboncoin.RoadToLeBonCoinApp
import com.technicaltest.roadtoleboncoin.data.local.AlbumsDao
import com.technicaltest.roadtoleboncoin.data.local.AlbumsDatabase
import com.technicaltest.roadtoleboncoin.data.remote.AlbumsService
import com.technicaltest.roadtoleboncoin.data.remote.AlbumsService.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): RoadToLeBonCoinApp {
        return app as RoadToLeBonCoinApp
    }

    @Provides
    fun providesDataBase(application: Application): AlbumsDatabase {
        return AlbumsDatabase.getInstance(application)
    }

    @Provides
    fun providesAlbumDao(albumsDatabase: AlbumsDatabase): AlbumsDao {
        return albumsDatabase.albumsDao()
    }
}
