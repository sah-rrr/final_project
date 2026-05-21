package com.example.lyceum_saturday10_2025.features.todo.data

import android.content.Context
import com.example.lyceum_saturday10_2025.common.JWTAuthenticator
import com.example.lyceum_saturday10_2025.common.UserPrefsManager
import com.example.lyceum_saturday10_2025.common.api.TokensApi
import com.example.lyceum_saturday10_2025.features.todo.data.model.TodoModel
import com.example.lyceum_saturday10_2025.features.todo.data.model.TodoRequest
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TodoRepository(val applicationContext: Context) {
    val prefs = UserPrefsManager(applicationContext)

    val api by lazy {
        getRetrofit()
    }

    suspend fun getItems(): List<TodoModel> {
        try {
            return api.getItems()
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    suspend fun addItem(text: String) {
        try {
            api.addItem(TodoRequest(text))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getRetrofit(): TodoApi {
        val httpClient = Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(logging)

        httpClient.addInterceptor { chain ->
            val currentToken = prefs.accessToken

            val newRequest = chain.request().newBuilder().apply {
                addHeader("Accept", "application/json")
                if (!currentToken.isNullOrEmpty()) {
                    addHeader("Authorization", "Bearer $currentToken")
                }
            }.build()

            chain.proceed(newRequest)
        }
            .authenticator(
                JWTAuthenticator(
                    context = applicationContext,
                    tokensApi = getTokensApi()
                )
            )

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(TodoApi::class.java)
    }

    private fun getTokensApi(): TokensApi {
        val httpClient = Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(logging)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
        return retrofit.create(TokensApi::class.java)
    }
}