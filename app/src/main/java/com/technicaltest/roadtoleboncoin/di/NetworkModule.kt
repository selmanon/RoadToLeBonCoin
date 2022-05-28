package com.technicaltest.roadtoleboncoin.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.technicaltest.roadtoleboncoin.RoadToLeBonCoinApp
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
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): RoadToLeBonCoinApp {
        return app as RoadToLeBonCoinApp
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }


    @Provides
    @Singleton
    fun provideContext(application: RoadToLeBonCoinApp): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): AlbumsService {
        return retrofit.create(AlbumsService::class.java)
    }
}
