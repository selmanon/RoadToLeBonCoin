package com.technicaltest.roadtoleboncoin.albums

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.technicaltest.roadtoleboncoin.ViewModelFactory
import com.technicaltest.roadtoleboncoin.data.local.AlbumsDatabase
import com.technicaltest.roadtoleboncoin.data.local.AlbumsLocalDataSource
import com.technicaltest.roadtoleboncoin.data.remote.AlbumsRemoteDataSource
import com.technicaltest.roadtoleboncoin.data.remote.AlbumsService
import com.technicaltest.roadtoleboncoin.data.source.DefaultAlbumsRepository
import com.technicaltest.roadtoleboncoin.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers.IO

class MainActivity : AppCompatActivity() {

    private val albumsViewModel: AlbumsViewModel by viewModels {
        ViewModelFactory(
            DefaultAlbumsRepository(
                AlbumsRemoteDataSource(AlbumsService.retrofit, IO),
                AlbumsLocalDataSource(AlbumsDatabase.getInstance(this).albumsDao(), IO),
                IO
            )
        )
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var albumsAdapter: AlbumsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        albumsViewModel.dataLoading.observe(this) {
            binding.progressBar.isVisible = it
        }

        albumsViewModel.albums.observe(this) {
            binding.userMessage.isVisible = false
            binding.progressBar.isVisible = false
            binding.recyclerView.isVisible = true

            albumsAdapter = AlbumsAdapter(it.toTypedArray())
            binding.recyclerView.adapter = albumsAdapter
        }

        albumsViewModel.error.observe(this) {
            if (it) {
                binding.userMessage.isVisible = it
                binding.recyclerView.isVisible = !it
                binding.progressBar.isVisible = !it

                binding.userMessage.error = "Unknown error"
            }

        }

        albumsViewModel.empty.observe(this) {
            if (it) {
                binding.userMessage.isVisible = it
                binding.recyclerView.isVisible = !it
                binding.progressBar.isVisible = !it

                binding.userMessage.text = "No data to show"
            }
        }

    }


    override fun onStart() {
        super.onStart()
        albumsViewModel.loadAlbums(forceUpdate = true)
    }
}