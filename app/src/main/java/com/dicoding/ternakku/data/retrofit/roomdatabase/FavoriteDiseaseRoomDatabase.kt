package com.dicoding.ternakku.data.retrofit.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteDisease::class], version = 1 )
abstract class FavoriteDiseaseRoomDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        var INSTANCE: FavoriteDiseaseRoomDatabase? = null

        fun getDataBase(context: Context): FavoriteDiseaseRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(FavoriteDiseaseRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteDiseaseRoomDatabase::class.java,
                        "favorite_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}