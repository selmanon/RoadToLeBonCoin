package com.technicaltest.roadtoleboncoin.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Immutable model class for a Album.
 */
@Entity(tableName = "albums")
data class AlbumEntity(
    @ColumnInfo val albumId: Int,
    @PrimaryKey val id: Int,
    @ColumnInfo val title: String?,
    @ColumnInfo val url: String?,
    @ColumnInfo val thumbnailUrl: String?
)