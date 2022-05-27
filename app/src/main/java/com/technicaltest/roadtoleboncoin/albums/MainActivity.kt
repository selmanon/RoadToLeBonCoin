package com.technicaltest.roadtoleboncoin.albums

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.technicaltest.roadtoleboncoin.R
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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        albumsViewModel.dataLoading.observe(this) {
            binding.progressBar.isVisible = it
        }

        albumsViewModel.albums.observe(this) {
            binding.userMessage.isVisible = false
            binding.progressBar.isVisible = false
            binding.recyclerView.isVisible = true

            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            albumsAdapter = AlbumsAdapter(it.toTypedArray(), this)
            binding.recyclerView.adapter = albumsAdapter

        }

        albumsViewModel.error.observe(this) {
            if (it) {
                binding.userMessage.isVisible = it
                binding.recyclerView.isVisible = !it
                binding.progressBar.isVisible = !it

                binding.userMessage.error = getString(R.string.error_label)
            }

        }

        albumsViewModel.empty.observe(this) {
            if (it) {
                binding.userMessage.isVisible = it
                binding.recyclerView.isVisible = !it
                binding.progressBar.isVisible = !it

                binding.userMessage.text = getString(R.string.empty_text_label)
            }
        }

    }


    override fun onStart() {
        super.onStart()
        albumsViewModel.loadAlbums(forceUpdate = true)
    }
}