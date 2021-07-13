package com.bartollesina.movieapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey val imdbId: String,
    val title: String,
    val posterUrl: String,
    val year: Int
)