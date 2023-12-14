package com.example.tugasfinal_d121211071.data.repository

import com.example.tugasfinal_d121211071.data.models.Disney
import com.example.tugasfinal_d121211071.data.response.GetDisneyResponse
import com.example.tugasfinal_d121211071.data.source.remote.DisneyApiService

class DisneyRepository(private val apiService: DisneyApiService) {

    suspend fun getDisney(): List<Disney> {
        return apiService.getDisney()
    }
}