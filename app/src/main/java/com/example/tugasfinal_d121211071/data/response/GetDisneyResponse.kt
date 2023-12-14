package com.example.tugasfinal_d121211071.data.response

import com.example.tugasfinal_d121211071.data.models.Disney
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetDisneyResponse(
    @SerialName("results")
    val results: List<Disney>,
)