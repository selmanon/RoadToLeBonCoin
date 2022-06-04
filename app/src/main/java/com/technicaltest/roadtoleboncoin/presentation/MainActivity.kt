package com.technicaltest.roadtoleboncoin.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.technicaltest.roadtoleboncoin.R
import com.technicaltest.roadtoleboncoin.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val albumsViewModel: AlbumsViewModel by viewModels()

    private lateinit var _binding: ActivityMainBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding


    private lateinit var albumsAdapter: AlbumsAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}