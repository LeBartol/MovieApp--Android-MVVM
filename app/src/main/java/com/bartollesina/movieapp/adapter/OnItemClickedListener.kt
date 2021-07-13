package com.bartollesina.movieapp.adapter

import com.bartollesina.movieapp.search_movie.MovieSingleUi

interface OnItemClickedListener{
    fun onItemClick(id:String)
    fun onSaveClick(movieSingleUi: MovieSingleUi)
}