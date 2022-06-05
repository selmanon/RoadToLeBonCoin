package com.technicaltest.roadtoleboncoin.domain.usecase

import com.technicaltest.roadtoleboncoin.domain.repositories.DefaultAlbumsRepository

class GetAlbumsUseCase(private val repository: DefaultAlbumsRepository) {
    suspend fun invoke() = repository.getAlbums()

}