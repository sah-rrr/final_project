package com.example.lyceum_saturday10_2025.features.auth.data

import com.example.lyceum_saturday10_2025.features.auth.data.model.AuthRequest
import com.example.lyceum_saturday10_2025.features.auth.data.model.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/register")
    suspend fun register(@Body request: AuthRequest): AuthResponse

    @POST("/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse
}