package com.dicoding.ternakku.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDao
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDisease
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDiseaseRoomDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var favoriteDao: FavoriteDao?
    private var favoriteDb: FavoriteDiseaseRoomDatabase?

    init {
        favoriteDb = FavoriteDiseaseRoomDatabase.getDataBase(application)
        favoriteDao = favoriteDb?.favoriteDao()
    }

    fun getFavoriteDiseases(): LiveData<List<FavoriteDisease>>?{
        return favoriteDao?.getFavoriteDisease()
    }
}