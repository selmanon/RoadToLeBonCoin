package com.technicaltest.roadtoleboncoin.domain.mappers

import com.technicaltest.roadtoleboncoin.data.AlbumEntity
import com.technicaltest.roadtoleboncoin.domain.model.Album


class AlbumMapper {
    fun mapAlbumEntitiesToDomain(albumEntities: List<AlbumEntity>): MutableList<Album> {
        val albums = mutableListOf<Album>()
        albumEntities.forEach {
            albums.add(Album(it.title ?: "", it.url ?: "", it.thumbnailUrl ?: ""))
        }
    return albums
    }

}