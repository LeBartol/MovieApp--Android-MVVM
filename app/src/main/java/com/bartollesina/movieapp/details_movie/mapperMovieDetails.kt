package com.bartollesina.movieapp.details_movie

import com.bartollesina.movieapp.models.MovieDetails

fun mapFromMovieDetailsToUi(movieDetails: MovieDetails): MovieDetailsUi {
    return MovieDetailsUi(
        title = movieDetails.title,
        released = movieDetails.released,
        runtime = movieDetails.runtime,
        imdbRating = movieDetails.imdbRating + "/10",
        genre = movieDetails.genre,
        plot = movieDetails.plot,
        poster = movieDetails.poster,
        imdbID = movieDetails.imdbID
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
    val imdbID: String
)