package com.example.lyceum_saturday10_2025.features.auth.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lyceum_saturday10_2025.features.auth.data.model.AuthRequest
import com.example.lyceum_saturday10_2025.common.UserPrefsManager
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.lyceum_saturday10_2025.features.auth.data.AuthApi

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = UserPrefsManager(application.applicationContext)

    private val authApi = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AuthApi::class.java)

    fun login(user: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = authApi.login(AuthRequest(user, pass))
                prefs.accessToken = response.accessToken
                prefs.username = user
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun register(user: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = authApi.register(AuthRequest(user, pass))
                prefs.accessToken = response.accessToken
                prefs.username = user
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}