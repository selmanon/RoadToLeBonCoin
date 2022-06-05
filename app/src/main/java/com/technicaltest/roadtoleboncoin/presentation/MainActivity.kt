package com.technicaltest.roadtoleboncoin.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.technicaltest.roadtoleboncoin.R
import com.technicaltest.roadtoleboncoin.databinding.ActivityMainBinding
import com.technicaltest.roadtoleboncoin.domain.model.Album
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val albumsViewModel: AlbumsViewModel by viewModels()

    private var _binding: ActivityMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var albumsAdapter: AlbumsAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


            albumsViewModel.albums.observe(this)  {
                binding.userMessage.isVisible = false
                binding.progressBar.isVisible = false
                binding.recyclerView.isVisible = true

                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                albumsAdapter = AlbumsAdapter(it, this)
                binding.recyclerView.adapter = albumsAdapter
        }

        albumsViewModel.albumUiState.observe(this) {
            when (it) {

                UiState.Loading -> {
                    binding.progressBar.isVisible = true
                }

                UiState.Empty -> {
                    binding.userMessage.isVisible = true
                    binding.recyclerView.isVisible = false
                    binding.progressBar.isVisible = false

                    binding.userMessage.text = getString(R.string.empty_text_label)
                }

                UiState.Error<String>("") -> {
                    binding.userMessage.isVisible = true
                    binding.recyclerView.isVisible = false
                    binding.progressBar.isVisible = false

                    binding.userMessage.text = getString(R.string.error_label)
                }



            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}