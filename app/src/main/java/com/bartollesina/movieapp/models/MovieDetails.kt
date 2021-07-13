package com.bartollesina.movieapp.models

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Released")
    val released: String,
    @SerializedName("Runtime")
    val runtime: String,
    val imdbRating: String,
    @SerializedName("Genre")
    val genre: String,
    @SerializedName("Plot")
    val plot: String,
    @SerializedName("Poster")
    val poster: String,
    val imdbID: String,
    @SerializedName("Year")
    val year: Int,
    @SerializedName("Response")
    val response: Boolean,
    @SerializedName("Error")
    val error: String?
)