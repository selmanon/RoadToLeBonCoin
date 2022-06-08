package com.technicaltest.roadtoleboncoin.domain.usecase

import com.technicaltest.roadtoleboncoin.data.Result
import com.technicaltest.roadtoleboncoin.data.source.AlbumsRepository
import com.technicaltest.roadtoleboncoin.domain.model.Album
import com.technicaltest.roadtoleboncoin.domain.mappers.AlbumMapper
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(
    private val repository: AlbumsRepository){
    suspend operator fun invoke(): Result<List<Album>>? {
        return repository.getAlbums()
    }

}