package com.bartollesina.movieapp.details_movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartollesina.movieapp.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsVM(private val movieRepository: MovieRepository) : ViewModel() {
    val data = MutableLiveData<MovieDetailsUi>()
    fun init(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            data.postValue(mapFromMovieDetailsToUi(movieRepository.getMovieById(id)))
        }
    }

    fun sendFavorite() {
    }
}