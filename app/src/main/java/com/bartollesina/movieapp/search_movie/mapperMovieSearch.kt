package com.bartollesina.movieapp.search_movie

import com.bartollesina.movieapp.models.MovieEntity
import com.bartollesina.movieapp.models.SearchResponse

fun mapFromSearchToUi(
    searchResponse: SearchResponse,
    favoritedList: List<MovieEntity>
): List<MovieSingleUi> {
    val yearsList = searchResponse.movies?.map { it.year }?.distinct()?.sorted()
    val movieListUi = mutableListOf<MovieSingleUi>()
    yearsList?.forEach { year ->
        movieListUi.add(
            MovieSingleUi(
                id = "",
                type = LayoutType.Header,
                title = "",
                year = year,
                posterUrl = ""
            )
        )
        val allMoviesFromYear = searchResponse.movies.filter { it.year == year }
        allMoviesFromYear.forEach {
            movieListUi.add(
                MovieSingleUi(
                    id = it.imdbID,
                    type = LayoutType.Movie,
                    title = it.title,
                    year = it.year,
                    posterUrl = it.poster,
                    favorited = favoritedList.find { favorite -> favorite.imdbId == it.imdbID } != null
                )
            )
        }
    }
    return movieListUi
}

data class MovieSingleUi(
    val id: String,
    val type: LayoutType,
    val title: String,
    val year: Int,
    val posterUrl: String,
    val favorited: Boolean = false
)

enum class LayoutType(type: Int) {
    Header(0), Movie(1), Empty(2)
}