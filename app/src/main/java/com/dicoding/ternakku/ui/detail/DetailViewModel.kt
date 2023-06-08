package com.dicoding.ternakku.ui.detail

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.ternakku.ListPenyakitAdapter
import com.dicoding.ternakku.MainActivity
import com.dicoding.ternakku.data.retrofit.ApiConfig
import com.dicoding.ternakku.data.retrofit.response.ListDiseasesResponse
import com.dicoding.ternakku.data.retrofit.response.ListDiseasesResponseItem
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

    fun insertFavoriteDisease(diseaseName : String, diseaseDetails : String, handlingMethod : String) {
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