package com.bartollesina.movieapp.networking

import com.bartollesina.movieapp.BuildConfig
import com.bartollesina.movieapp.models.MovieDetails
import com.bartollesina.movieapp.models.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieRestInterface {
    @GET("?apikey=${BuildConfig.API_KEY}&type=movie")
    suspend fun moviesSearch(@Query("s") name: String): SearchResponse

    @GET("?apikey=${BuildConfig.API_KEY}")
    suspend fun getMovieById(@Query("i") imdbId: String): MovieDetails
}