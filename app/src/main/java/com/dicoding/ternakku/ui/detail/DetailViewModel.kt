package com.dicoding.ternakku.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDao
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDesease
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDeseaseRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel (application: Application) : AndroidViewModel(application) {

    private var favoriteDao: FavoriteDao?
    private var favoriteDb: FavoriteDeseaseRoomDatabase?


    init {
        favoriteDb = FavoriteDeseaseRoomDatabase.getDataBase(application)
        favoriteDao = favoriteDb?.favoriteDao()
    }

    fun insertFavoriteDesease(id: Int, name: String, detail: String, handle: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val penyakit = FavoriteDesease(
                id,
                name,
                detail,
                handle
            )
            favoriteDao?.insertFavoriteDesease(penyakit)
        }
    }

    suspend fun cekFavoriteDesease(id: Int) = favoriteDao?.cekFavoriteDesease(id)

    fun deleteFavoriteDesease(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDao?.deleteFavoriteDesease(id)
        }
    }
}