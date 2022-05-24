package com.technicaltest.roadtoleboncoin.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Immutable model class for a Album.
 */
@Entity(tableName = "albums")
data class Album(
    @PrimaryKey val albumId: Int,
    @ColumnInfo val id: String?,
    @ColumnInfo val title: String?,
    @ColumnInfo val url: String?,
    @ColumnInfo val thumbnailUrl: String?
)