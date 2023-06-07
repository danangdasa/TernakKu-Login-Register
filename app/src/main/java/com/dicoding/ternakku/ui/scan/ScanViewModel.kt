package com.dicoding.ternakku.ui.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.ternakku.preference.AuthorizeModel
import com.dicoding.ternakku.preference.LoginPreference

class ScanViewModel(private val pref: LoginPreference): ViewModel() {

    fun getUserData():LiveData<AuthorizeModel>{
        return pref.getToken().asLiveData()
    }
}