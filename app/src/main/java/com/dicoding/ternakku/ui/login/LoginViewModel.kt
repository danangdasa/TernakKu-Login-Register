package com.dicoding.ternakku.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.ternakku.preference.AuthorizeModel
import com.dicoding.ternakku.preference.LoginPreference
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: LoginPreference) : ViewModel() {

    fun getUser(): LiveData<AuthorizeModel>{
        return pref.getToken().asLiveData()
    }

    fun saveUserData(userData: AuthorizeModel){
        viewModelScope.launch {
            pref.saveToken(userData)
        }
    }

    fun login(){
        viewModelScope.launch {
            pref.login()
        }
    }

}