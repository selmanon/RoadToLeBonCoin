package com.technicaltest.roadtoleboncoin.domain.mappers

import com.technicaltest.roadtoleboncoin.data.AlbumEntity
import com.technicaltest.roadtoleboncoin.domain.model.Album


class AlbumMapper {
    fun mapAlbumEntityToDomain(albumEntity: AlbumEntity) =
        Album(albumEntity.title ?: "", albumEntity.url ?: "", albumEntity.thumbnailUrl ?: "")

}