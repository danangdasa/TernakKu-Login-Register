package com.dicoding.ternakku.data.retrofit.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_desease")
data class FavoriteDisease (
    @PrimaryKey
    val diseaseName : String,
    val diseaseDetails : String,
    val handlingMethod : String

    ): Serializable