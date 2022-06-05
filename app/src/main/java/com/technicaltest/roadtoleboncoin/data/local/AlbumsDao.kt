package com.technicaltest.roadtoleboncoin.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.technicaltest.roadtoleboncoin.data.AlbumEntity

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
    fun observeAlbums() : LiveData<List<AlbumEntity>>

    @Query("Select * FROM albums")
    suspend fun getAllAlbums() : List<AlbumEntity>

    /**
     * Insert an album in the database. If the album already exists, replace it.
     *
     * @param albumEntity the album to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(albumEntity: AlbumEntity)

    /**
     * Delete all albums.
     */
    @Query("DELETE FROM albums")
    suspend fun deleteAlbums()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAlbums(order: List<AlbumEntity>)


}