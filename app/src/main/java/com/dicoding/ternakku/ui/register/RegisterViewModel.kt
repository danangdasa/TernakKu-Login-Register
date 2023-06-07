package com.dicoding.ternakku.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.ternakku.preference.Authorize
import com.dicoding.ternakku.preference.LoginPreference
import kotlinx.coroutines.launch

class RegisterViewModel (private val pref: LoginPreference) : ViewModel() {

    fun saveUser(login: Authorize){
        viewModelScope.launch {
            pref.saveUserData(login)
        }
    }
}