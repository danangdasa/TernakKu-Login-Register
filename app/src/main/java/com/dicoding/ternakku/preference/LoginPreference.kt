package com.dicoding.ternakku.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.*

class LoginPreference private constructor(private val dataStore: DataStore<Preferences>){

    suspend fun saveToken(userToken: AuthorizeModel){
        dataStore.edit { preference ->
            preference[TOKEN_KEY] = userToken.token
            preference[STATE_KEY] = userToken.isLogin
        }
    }

    fun getToken(): Flow<AuthorizeModel>{
        return dataStore.data.map { preference ->
            AuthorizeModel(
                preference[TOKEN_KEY] ?: "",
                preference[STATE_KEY] ?: false
            )
        }
    }

    suspend fun login(){
        dataStore.edit { preference ->
            preference[STATE_KEY] = true
        }
    }

    suspend fun logout(){
        dataStore.edit { preference ->
            preference[STATE_KEY] = false
            preference[TOKEN_KEY] = ""
        }
    }

    suspend fun deleteToken(){
        dataStore.edit { token ->
            token[TOKEN_KEY] = ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: LoginPreference? = null

        private val STATE_KEY = booleanPreferencesKey("state")
        private val TOKEN_KEY = stringPreferencesKey("token")

        fun getInstance(dataStore: DataStore<Preferences>): LoginPreference{
            return INSTANCE ?: synchronized(this){
                val instance = LoginPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}