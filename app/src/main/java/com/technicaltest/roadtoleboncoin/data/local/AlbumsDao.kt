package com.technicaltest.roadtoleboncoin.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.technicaltest.roadtoleboncoin.data.Album

/**
 * Data Access object for albums table.
 */
@Dao
interface AlbumsDao {

    /**
     * Observes list of albums.
     *
     * @return all albums.
     */
    @Query("Select * FROM albums")
    fun observeAlbums() : LiveData<List<Album>>

    @Query("Select * FROM albums")
    suspend fun getAllAlbums() : List<Album>

    /**
     * Insert an album in the database. If the album already exists, replace it.
     *
     * @param album the album to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: Album)

    /**
     * Delete all albums.
     */
    @Query("DELETE FROM albums")
    suspend fun deleteAlbums()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAlbums(order: List<Album>)


}