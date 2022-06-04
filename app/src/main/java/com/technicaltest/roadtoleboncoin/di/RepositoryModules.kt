package com.technicaltest.roadtoleboncoin.di

import com.technicaltest.roadtoleboncoin.data.local.AlbumsDao
import com.technicaltest.roadtoleboncoin.data.local.AlbumsLocalDataSource
import com.technicaltest.roadtoleboncoin.data.remote.AlbumsRemoteDataSource
import com.technicaltest.roadtoleboncoin.data.remote.AlbumsService
import com.technicaltest.roadtoleboncoin.data.source.AlbumsRepository
import com.technicaltest.roadtoleboncoin.data.source.DefaultAlbumsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModules {

    @Provides
    @Singleton
    fun provideAlbumsLocalDataSource(albumsDao: AlbumsDao, ioDispatcher: CoroutineDispatcher) = AlbumsLocalDataSource(albumsDao, ioDispatcher)

    @Provides
    @Singleton
    fun provideAlbumsRemoteDataSource(albumsService: AlbumsService, ioDispatcher: CoroutineDispatcher) = AlbumsRemoteDataSource(albumsService, ioDispatcher)

    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideDefaultAlbumRepository(remoteDataSource: AlbumsRemoteDataSource, localDataSource: AlbumsLocalDataSource,ioDispatcher: CoroutineDispatcher) : AlbumsRepository =
        DefaultAlbumsRepository(remoteDataSource, localDataSource, ioDispatcher)
}
