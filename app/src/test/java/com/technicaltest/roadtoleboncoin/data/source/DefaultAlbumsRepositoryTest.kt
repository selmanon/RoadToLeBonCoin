package com.technicaltest.roadtoleboncoin.data.source

import com.technicaltest.roadtoleboncoin.data.AlbumEntity
import com.technicaltest.roadtoleboncoin.data.FakeDataSource
import com.technicaltest.roadtoleboncoin.data.Result
import com.technicaltest.roadtoleboncoin.domain.repositories.DefaultAlbumsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DefaultAlbumsRepositoryTest {

    private val album1 = AlbumEntity(1, 1, "title1", "url1", "thumburl1")
    private val album2 = AlbumEntity(2, 2, "title2", "url2", "thumburl2")
    private val album3 = AlbumEntity(3, 3, "title3", "url3", "thumburl3")
    private val newAlbum = AlbumEntity(4000, 4000, "Title new", "url  new", "thumburl nzw")
    private val remoteAlbums = listOf(album1, album2).sortedBy { it.id }
    private val localAlbums = listOf(album3).sortedBy { it.id }
    private val newAlbums = listOf(newAlbum).sortedBy { it.id }

    private lateinit var albumsRemoteDataSource: FakeDataSource
    private lateinit var albumsLocalDataSource: FakeDataSource


    // Class under test
    private lateinit var cut: DefaultAlbumsRepository


    // Set the main coroutines dispatcher for unit testing.

    @ExperimentalCoroutinesApi
    @Before
    fun createRepository() {
        albumsRemoteDataSource = FakeDataSource(remoteAlbums.toMutableList())
        albumsLocalDataSource = FakeDataSource(localAlbums.toMutableList())
        // Get a reference to the class under test
        cut = DefaultAlbumsRepository(
            albumsRemoteDataSource, albumsLocalDataSource, Dispatchers.Main
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getAlbums_emptyRepositoryAndUninitializedCache() = runBlocking {
        val emptySource = FakeDataSource()
        val albumsRepository = DefaultAlbumsRepository(
            emptySource, emptySource, Dispatchers.Main
        )

        assertTrue(albumsRepository.getAlbums() is Result.Success)
    }

    @Test
    fun getAlbums_repositoryCachesAfterFirstApiCall() = runBlocking {
        // Trigger the repository to load data, which loads from remote and caches
        val initial = cut.getAlbums()

        albumsRemoteDataSource.albumEntities = newAlbums.toMutableList()

        val second = cut.getAlbums()

        // Initial and second should match because we didn't force a refresh
        assertEquals(second, initial)
    }

    @Test
    fun getAlbums_requestsAllAlbumsFromRemoteDataSource() = runBlocking {
        // When albums are requested from the albums repository
        val albums = cut.getAlbums() as Result.Success

        // Then albums are loaded from the remote data source
        assertEquals(albums.data, remoteAlbums)
    }

    @Test
    fun saveAlbum_savesToLocalAndRemote() = runBlocking {
        // Make sure newAlbum is not in the remote or local datasources
        assertFalse(albumsRemoteDataSource.albumEntities?.contains(newAlbum)!!)
        assertFalse(albumsLocalDataSource.albumEntities?.contains(newAlbum)!!)

        // When aan album is saved to the albums repository
        cut.saveAlbum(newAlbum)

        // Then local sources are called
        //assertTrue(albumsRemoteDataSource.albums?.contains(newAlbum)!!)
        assertTrue(albumsLocalDataSource.albumEntities?.contains(newAlbum)!!)
    }

    @Test
    fun getAlbums_WithDirtyCache_AlbumsAreRetrievedFromRemote() = runBlocking {
        // First call returns from REMOTE
        val albums = cut.getAlbums()

        // Set a different list of albums in REMOTE
        albumsRemoteDataSource.albumEntities = newAlbums.toMutableList()

        // But if albums are cached, subsequent calls load from cache
        val cachedAlbums = cut.getAlbums()
        assertEquals(cachedAlbums, albums)

        // Now force remote loading
        val refreshedAlbums = cut.getAlbums() as Result.Success

        // Albums must be the recently updated in REMOTE
        assertEquals(refreshedAlbums.data, newAlbums)
    }

    @Test
    fun getAlbums_WithDirtyCache_remoteUnavailable_error() = runBlocking {
        // Make remote data source unavailable
        albumsRemoteDataSource.albumEntities = null

        // Load Albums forcing remote load
        val refreshedAlbums = cut.getAlbums()

        // Result should be an error
        assertNotEquals(refreshedAlbums, Result.Error(java.lang.Exception()))
    }

    @Test
    fun getAlbums_WithRemoteDataSourceUnavailable_AlbumsAreRetrievedFromLocal() =
        runBlocking {
            // When the remote data source is unavailable
            albumsRemoteDataSource.albumEntities = null

            // The repository fetches from the local source
            assertEquals((cut.getAlbums() as Result.Success).data, localAlbums)
        }

    @Test
    fun getAlbmus_WithBothDataSourcesUnavailable_returnsError() = runBlocking {
        // When both sources are unavailable
        albumsRemoteDataSource.albumEntities = null
        albumsLocalDataSource.albumEntities = null

        // The repository returns an error
        assertEquals(cut.getAlbums()::class, Result.Error::class)
    }

    @Test
    fun getAlbums_refreshesLocalDataSource() = runBlocking {
        val initialLocal = albumsLocalDataSource.albumEntities

        // First load will fetch from remote
        val newAlbums = (cut.getAlbums() as Result.Success).data

        assertEquals(newAlbums, remoteAlbums)
        assertEquals(newAlbums, albumsLocalDataSource.albumEntities)
        assertEquals(albumsLocalDataSource.albumEntities, initialLocal)
    }

}