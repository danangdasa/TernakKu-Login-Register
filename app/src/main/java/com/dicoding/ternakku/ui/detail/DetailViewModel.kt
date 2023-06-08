package com.dicoding.ternakku.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDao
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDisease
import com.dicoding.ternakku.data.retrofit.roomdatabase.FavoriteDiseaseRoomDatabase
import com.dicoding.ternakku.preference.AuthorizeModel
import com.dicoding.ternakku.preference.LoginPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private var favoriteDao: FavoriteDao?
    private var favoriteDb: FavoriteDiseaseRoomDatabase?


    init {
        favoriteDb = FavoriteDiseaseRoomDatabase.getDataBase(application)
        favoriteDao = favoriteDb?.favoriteDao()
    }

    fun insertFavoriteDisease(diseaseName: String, diseaseDetails: String, handlingMethod: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val penyakit = FavoriteDisease(
                diseaseName,
                diseaseDetails,
                handlingMethod
            )
            favoriteDao?.insertFavoriteDisease(penyakit)
        }
    }

    suspend fun cekFavoriteDisease(diseaseName: String) = favoriteDao?.cekFavoriteDisease(diseaseName)

    fun deleteFavoriteDisease(diseaseName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDao?.deleteFavoriteDisease(diseaseName)
        }
    }
}