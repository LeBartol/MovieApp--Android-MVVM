package com.bartollesina.movieapp.repository

import com.bartollesina.movieapp.models.MovieDetails
import com.bartollesina.movieapp.models.SearchResponse
import com.bartollesina.movieapp.networking.MovieRestInterface

class MovieRepository(private val movieRestInterface: MovieRestInterface) {

    suspend fun getMoviesSearch(name: String): SearchResponse {
        return movieRestInterface.moviesSearch(name)
    }

    suspend fun getMovieById(id: String): MovieDetails {
        return movieRestInterface.getMovieById(id)
    }

}