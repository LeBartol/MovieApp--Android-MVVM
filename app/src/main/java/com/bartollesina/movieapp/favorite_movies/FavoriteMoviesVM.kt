package com.bartollesina.movieapp.favorite_movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartollesina.movieapp.adapter.OnItemClickedListener
import com.bartollesina.movieapp.repository.MovieRepository
import com.bartollesina.movieapp.search_movie.MovieSingleUi
import com.bartollesina.movieapp.utils.ResourceProvider
import com.bartollesina.movieapp.utils.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteMoviesVM(
    private val movieRepository: MovieRepository,
    private val resourceProvider: ResourceProvider
) : OnItemClickedListener,
    ViewModel() {
    val data = MutableLiveData<List<MovieSingleUi>>()
    val openDetails = SingleLiveData<String>()

    fun getAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            data.postValue(mapFromFavoritesToUi(movieRepository.getAllMovies(), resourceProvider))
        }
    }

    override fun onItemClick(id: String) {
        openDetails.value = id
    }

    override fun onSaveClick(movieSingleUi: MovieSingleUi) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.insertOrDelete(mapFromSingleUiToEntity(movieSingleUi))
            getAllMovies()
        }
    }
}