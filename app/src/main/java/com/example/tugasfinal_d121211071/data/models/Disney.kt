package com.example.tugasfinal_d121211071.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class Disney(
    @SerialName("directors")
    val directors: String?,
    @SerialName("genre")
    val genre: String?,
    @SerialName("image")
    val image: String?,
    @SerialName("link")
    val link: String?,
    @SerialName("metascore")
    val metascore: String?,
    @SerialName("movieId")
    val movieId: Int?,
    @SerialName("rating")
    val rating: String?,
    @SerialName("runtime")
    val runtime: String?,
    @SerialName("stars")
    val stars: String?,
    @SerialName("summary")
    val summary: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("year")
    val year: String?
) : Parcelable
