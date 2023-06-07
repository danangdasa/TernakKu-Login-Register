package com.dicoding.ternakku.data.retrofit.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_desease")
data class FavoriteDisease (
    @PrimaryKey
    val id : Int,
    val name : String,
    val detail : String,
    val handle : String
): Serializable