package com.dicoding.ternakku.data.retrofit.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FavoriteDesease::class], version = 1)
abstract class FavoriteDeseaseRoomDatabase : RoomDatabase() {
    abstract fun favoriteDao() : FavoriteDao

    companion object{
        var INSTANCE : FavoriteDeseaseRoomDatabase? = null

        fun getDataBase(context: Context): FavoriteDeseaseRoomDatabase?{
            if (INSTANCE==null){
                synchronized(FavoriteDeseaseRoomDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, FavoriteDeseaseRoomDatabase::class.java, "favorite_database").build()
                }
            }
            return INSTANCE
        }
    }
}