package com.dicoding.ternakku.data.retrofit.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insertFavoriteDesease(favoriteDesease: FavoriteDesease)

    @Query("SELECT * FROM favorite_desease")
    fun getFavoriteDesease(): LiveData<List<FavoriteDesease>>

    @Query("SELECT count(*) FROM favorite_desease WHERE favorite_desease.id = :id")
    suspend fun cekFavoriteDesease(id : Int) : Int

    @Query("DELETE FROM favorite_desease WHERE favorite_desease.id = :id")
    suspend fun deleteFavoriteDesease(id: Int) : Int
}