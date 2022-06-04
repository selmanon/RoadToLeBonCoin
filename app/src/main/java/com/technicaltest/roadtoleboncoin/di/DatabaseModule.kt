package com.technicaltest.roadtoleboncoin.di

import android.app.Application
import android.content.Context
import com.technicaltest.roadtoleboncoin.RoadToLeBonCoinApp
import com.technicaltest.roadtoleboncoin.data.local.AlbumsDao
import com.technicaltest.roadtoleboncoin.data.local.AlbumsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
