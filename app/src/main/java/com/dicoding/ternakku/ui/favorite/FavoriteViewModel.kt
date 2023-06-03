package com.dicoding.ternakku.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDao
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDesease
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDeseaseRoomDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var favoriteDao: FavoriteDao?
    private var favoriteDb: FavoriteDeseaseRoomDatabase?

    init {
        favoriteDb = FavoriteDeseaseRoomDatabase.getDataBase(application)
        favoriteDao = favoriteDb?.favoriteDao()
    }

    fun getFavoriteDeseases(): LiveData<List<FavoriteDesease>>?{
        return favoriteDao?.getFavoriteDesease()
    }
}