package com.bartollesina.movieapp.favorite_movies

import com.bartollesina.movieapp.R
import com.bartollesina.movieapp.models.MovieEntity
import com.bartollesina.movieapp.search_movie.LayoutType
import com.bartollesina.movieapp.search_movie.MovieSingleUi
import com.bartollesina.movieapp.utils.ResourceProvider

fun mapFromFavoritesToUi(
    movieEntities: List<MovieEntity>,
    resourceProvider: ResourceProvider
): List<MovieSingleUi> {
    val movieListUi = mutableListOf<MovieSingleUi>()
    if (movieEntities.isEmpty()) {
        movieListUi.add(
            MovieSingleUi(
                id = "",
                type = LayoutType.Empty,
                title = resourceProvider.getString(R.string.no_favorites),
                year = -1,
                posterUrl = ""
            )
        )
    }
    movieListUi.addAll(
        movieEntities.map {
            MovieSingleUi(
                id = it.imdbId,
                type = LayoutType.Movie,
                title = it.title,
                year = it.year,
                posterUrl = it.posterUrl,
                favorited = true
            )
        })
    return movieListUi
}

fun mapFromSingleUiToEntity(movieSingleUi: MovieSingleUi): MovieEntity {
    return MovieEntity(
        imdbId = movieSingleUi.id,
        title = movieSingleUi.title,
        posterUrl = movieSingleUi.posterUrl,
        year = movieSingleUi.year
    )
}