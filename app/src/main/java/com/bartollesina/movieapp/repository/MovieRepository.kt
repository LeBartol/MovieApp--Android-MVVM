package com.bartollesina.movieapp.repository

import com.bartollesina.movieapp.database.MovieDao
import com.bartollesina.movieapp.models.MovieDetails
import com.bartollesina.movieapp.models.MovieEntity
import com.bartollesina.movieapp.models.SearchResponse
import com.bartollesina.movieapp.networking.MovieRestInterface
import com.bartollesina.movieapp.search_movie.MovieSingleUi

class MovieRepository(
    private val movieDao: MovieDao,
    private val movieRestInterface: MovieRestInterface
) {
    suspend fun getAllMovies(): List<MovieEntity> {
        return movieDao.getAllMovies()
    }

    suspend fun getMoviesSearch(name: String): SearchResponse {
        return movieRestInterface.moviesSearch(name)
    }

    suspend fun getMovieById(id: String): MovieDetails {
        return movieRestInterface.getMovieById(id)
    }

    suspend fun insertOrDelete(movieEntity: MovieEntity) {
        val movie = movieDao.getAllMovies().find { it.imdbId == movieEntity.imdbId }
        if (movie != null) {
            movieDao.deleteMovieById(movie.imdbId)
        } else {
            movieDao.insertMovie(movieEntity)
        }
    }


}