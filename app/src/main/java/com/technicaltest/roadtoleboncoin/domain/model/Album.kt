package com.technicaltest.roadtoleboncoin.domain.model

import androidx.room.ColumnInfo

/**
 * Representation for a [Album] fetched from an external layer data source
 */
data class Album( val title: String,
                  val url: String,
                  val thumbnailUrl: String)