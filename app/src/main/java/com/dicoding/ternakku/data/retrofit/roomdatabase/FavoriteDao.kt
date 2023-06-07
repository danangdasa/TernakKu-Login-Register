package com.dicoding.ternakku.data.retrofit.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insertFavoriteDisease(favoriteDisease: FavoriteDisease)

    @Query("SELECT * FROM favorite_desease")
    fun getFavoriteDisease(): LiveData<List<FavoriteDisease>>

    @Query("SELECT count(*) FROM favorite_desease WHERE favorite_desease.id = :id")
    suspend fun cekFavoriteDisease(id : Int) : Int

    @Query("DELETE FROM favorite_desease WHERE favorite_desease.id = :id")
    suspend fun deleteFavoriteDisease(id: Int) : Int
}