package com.dicoding.ternakku

import androidx.lifecycle.*
import com.dicoding.ternakku.preference.Authorize
import com.dicoding.ternakku.preference.LoginPreference
import kotlinx.coroutines.launch

class MainViewModel(private val pref: LoginPreference): ViewModel() {

    fun getLoginUser(): LiveData<Authorize> {
        return pref.getUserData().asLiveData()
    }

    fun logout(){
        viewModelScope.launch {
            pref.logout()
        }
    }

    fun deleteToken(){
        viewModelScope.launch {
            pref.deleteToken()
        }
    }
}