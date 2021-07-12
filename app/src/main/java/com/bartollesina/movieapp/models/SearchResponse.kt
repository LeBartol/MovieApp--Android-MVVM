package com.bartollesina.movieapp.models

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("Response")
    val response: Boolean,
    @SerializedName("Search")
    val movies: List<Movie>?,
    val totalResults: String?,
    @SerializedName("Error")
    val error: String?
)