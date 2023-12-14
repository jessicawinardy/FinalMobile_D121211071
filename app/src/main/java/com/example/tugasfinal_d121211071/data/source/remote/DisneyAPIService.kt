package com.example.tugasfinal_d121211071.data.source.remote

import com.example.tugasfinal_d121211071.data.models.Disney
import com.example.tugasfinal_d121211071.data.response.GetDisneyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DisneyApiService {
    @GET("movies/all?details=true")
    suspend fun getDisney(
    ): List<Disney>
}