package com.bartollesina.movieapp.details_movie

import com.bartollesina.movieapp.models.MovieDetails
import com.bartollesina.movieapp.models.MovieEntity

fun mapFromMovieDetailsToUi(
    movieDetails: MovieDetails,
    allMovies: List<MovieEntity>
): MovieDetailsUi {
    return MovieDetailsUi(
        title = movieDetails.title,
        released = movieDetails.released,
        runtime = movieDetails.runtime,
        imdbRating = movieDetails.imdbRating + "/10",
        genre = movieDetails.genre,
        plot = movieDetails.plot,
        poster = movieDetails.poster,
        imdbID = movieDetails.imdbID,
        year = movieDetails.year,
        favorited = allMovies.find { it.imdbId == movieDetails.imdbID } != null
    )
}

data class MovieDetailsUi(
    val title: String,
    val released: String,
    val runtime: String,
    val imdbRating: String,
    val genre: String,
    val plot: String,
    val poster: String,
    val imdbID: String,
    val year: Int,
    var favorited: Boolean
)

fun mapFromDetailsUiToEntity(movieDetailsUi: MovieDetailsUi): MovieEntity {
    return MovieEntity(
        imdbId = movieDetailsUi.imdbID,
        title = movieDetailsUi.title,
        posterUrl = movieDetailsUi.poster,
        year = movieDetailsUi.year
    )
}