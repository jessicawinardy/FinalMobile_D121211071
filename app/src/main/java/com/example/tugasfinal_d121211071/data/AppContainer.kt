package com.example.tugasfinal_d121211071.data

import com.example.tugasfinal_d121211071.data.repository.DisneyRepository
import com.example.tugasfinal_d121211071.data.source.remote.DisneyApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val disneyRepository: DisneyRepository
}

class DefaultAppContainer: AppContainer {

    private val BASE_URL = "https://apidisneymovies.bsite.net/api/v1/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService : DisneyApiService by lazy {
        retrofit.create(DisneyApiService::class.java)
    }

    override val disneyRepository: DisneyRepository
        get() = DisneyRepository(retrofitService)
}

