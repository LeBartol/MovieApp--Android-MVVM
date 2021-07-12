package com.bartollesina.movieapp.models

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Year")
    val year: Int = -1,
    val imdbID: String
)