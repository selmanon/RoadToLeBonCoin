package com.technicaltest.roadtoleboncoin.albums

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.MainCoroutineRule
import com.technicaltest.FakeFailingAlbumsRemoteDataSource
import com.technicaltest.FakeRepository
import com.technicaltest.getOrAwaitValue
import com.technicaltest.observeForTesting
import com.technicaltest.roadtoleboncoin.data.AlbumEntity
import com.technicaltest.roadtoleboncoin.domain.mappers.AlbumMapper
import com.technicaltest.roadtoleboncoin.domain.repositories.DefaultAlbumsRepository
import com.technicaltest.roadtoleboncoin.domain.usecase.GetAlbumsUseCase
import com.technicaltest.roadtoleboncoin.presentation.AlbumsViewModel
import com.technicaltest.roadtoleboncoin.presentation.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.hamcrest.CoreMatchers.any
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import java.lang.Exception

/**
 * Unit tests for the implementation of [AlbumsViewModel]
 */
@ExperimentalCoroutinesApi
class AlbumsViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Subject under test
    private lateinit var albumsViewModel: AlbumsViewModel

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // Use a fake repository to be injected into the viewmodel
    private lateinit var albumsRepository: FakeRepository



    @Before
    fun setupAlbumsViewModel() {
        albumsRepository = FakeRepository()

        albumsViewModel = AlbumsViewModel(GetAlbumsUseCase(albumsRepository))
    }

    @Test
    fun loadEmptyAlbumFromRepository_EmptyResults() = runBlocking {
        // Given an initialized albumsViewModel with no album

        // Then the results are empty
        assertTrue(albumsViewModel.albumUiState.value is UiState.Empty)
    }

    @Test
    fun loadAlbums_error() = runTest {
        // Make the repository return errors
        albumsRepository.setReturnError(true)

        // Load album
        albumsViewModel.loadAlbums()
        // Observe the items to keep LiveData emitting
        albumsViewModel.albumUiState.observeForTesting {

            // Then progress indicator is hidden
            assertFalse(albumsViewModel.albumUiState.value is UiState.Error)



        }
    }

    @Test
    fun loadNonEmptyalbumFromRepository_NonEmptyResults() {
        // We initialise the album to 3, with one active and two completed
        val album1 = AlbumEntity(1, 1, "Title1", "url1", "thumurl1")
        val album2 = AlbumEntity(2, 2, "Title2", "url2", "thumurl2")
        val album3 = AlbumEntity(3, 3, "Title3", "url3", "thumurl3")
        albumsRepository.addAlbums(album1, album2, album3)

        // Then the results are not empty
        assertFalse(albumsViewModel.albumUiState.value is UiState.Empty)
    }

    @Test
    fun loadEmptyAlbumsFromRepository_EmptyResults() = runTest {

        val emptyViewModel = AlbumsViewModel(GetAlbumsUseCase(albumsRepository))

        assertTrue(albumsViewModel.albumUiState.value is UiState.Empty)


        // Then an error message is shown

    }

    @Test
    fun loadAlbum_loading() = runTest {
        // Set Main dispatcher to not run coroutines eagerly, for just this one test
        Dispatchers.setMain(StandardTestDispatcher())

        // Load the task in the viewmodel
        albumsViewModel.loadAlbums()

        albumsViewModel.albumUiState.observeForTesting {
            // Then progress indicator is shown
            assertTrue(albumsViewModel.albumUiState.value is UiState.Loading)

            // Execute pending coroutines actions
            advanceUntilIdle()

            // Then progress indicator is hidden
            assertFalse(albumsViewModel.albumUiState.value is UiState.Loading)

        }


    }
}