package com.dicoding.ternakku.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDao
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDisease
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDiseaseRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel (application: Application) : AndroidViewModel(application) {

    private var favoriteDao: FavoriteDao?
    private var favoriteDb: FavoriteDiseaseRoomDatabase?


    init {
        favoriteDb = FavoriteDiseaseRoomDatabase.getDataBase(application)
        favoriteDao = favoriteDb?.favoriteDao()
    }

    fun insertFavoriteDisease(id: Int, name: String, detail: String, handle: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val penyakit = FavoriteDisease(
                id,
                name,
                detail,
                handle
            )
            favoriteDao?.insertFavoriteDisease(penyakit)
        }
    }

    suspend fun cekFavoriteDisease(id: Int) = favoriteDao?.cekFavoriteDisease(id)

    fun deleteFavoriteDisease(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDao?.deleteFavoriteDisease(id)
        }
    }
}