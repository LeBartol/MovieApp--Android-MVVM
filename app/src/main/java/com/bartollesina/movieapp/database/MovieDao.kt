package com.bartollesina.movieapp.database

import androidx.room.*
import com.bartollesina.movieapp.models.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Query("DELETE FROM movie_table WHERE imdbId = :id")
    suspend fun deleteMovieById(id:String)

    @Query("SELECT * FROM movie_table")
    suspend fun getAllMovies(): List<MovieEntity>

}