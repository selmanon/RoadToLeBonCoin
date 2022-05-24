package com.technicaltest.roadtoleboncoin.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.technicaltest.roadtoleboncoin.data.Album

/**
 * The Room Database that contains the Albums table.
 */
@Database(entities = [Album::class], version = 1)
abstract class AlbumsDatabase : RoomDatabase() {

    abstract fun albumsDao(): AlbumsDao

    companion object {
        private var INSTANCE: AlbumsDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): AlbumsDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AlbumsDatabase::class.java, "albums_database.db"
                    ).build()
                }

                return INSTANCE!!
            }
        }
    }
}