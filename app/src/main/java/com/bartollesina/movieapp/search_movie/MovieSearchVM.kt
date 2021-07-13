package com.bartollesina.movieapp.search_movie


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartollesina.movieapp.adapter.OnItemClickedListener
import com.bartollesina.movieapp.favorite_movies.mapFromSingleUiToEntity
import com.bartollesina.movieapp.repository.MovieRepository
import com.bartollesina.movieapp.utils.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
class MovieSearchVM(private val movieRepository: MovieRepository) : OnItemClickedListener,
    ViewModel() {
    val searchData = MutableLiveData<List<MovieSingleUi>>()
    val openDetails = SingleLiveData<String>()
    private val channelText = MutableSharedFlow<String>(1)
    private var lastSearch = ""

    init {
        viewModelScope.launch(Dispatchers.IO) {
            observeText()
        }
    }

    fun sendSearch(text: String) {
        lastSearch = text
        viewModelScope.launch(Dispatchers.IO) {
            channelText.tryEmit(text)
        }
    }

    private suspend fun observeText() {
        channelText
            .debounce(300)
            .map { Pair(movieRepository.getMoviesSearch(it), movieRepository.getAllMovies()) }
            .map { mapFromSearchToUi(it.first, it.second) }
            .collect {
                searchData.postValue(it)
            }
    }

    override fun onItemClick(id: String) {
        openDetails.value = id
    }

    override fun onSaveClick(movieSingleUi: MovieSingleUi) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.insertOrDelete(mapFromSingleUiToEntity(movieSingleUi))
            channelText.tryEmit(lastSearch)
        }
    }
}
