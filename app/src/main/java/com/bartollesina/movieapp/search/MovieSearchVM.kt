package com.bartollesina.movieapp.search


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartollesina.movieapp.repository.MovieRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch



@FlowPreview
class MovieSearchVM(private val movieRepository: MovieRepository) : ViewModel() {
    val searchData = MutableLiveData<List<MovieSingleUi>>()
    private val channelText = BroadcastChannel<String>(1)

    init {
        viewModelScope.launch {
            observeText()
        }
    }

    fun sendSearch(text: String) {
        viewModelScope.launch {
            channelText.send(text)
        }
    }

    private suspend fun observeText() {
        channelText
            .asFlow()
            .debounce(300)
            .map { movieRepository.getMoviesSearch(it) }
            .map { mapFromSearchToUi(it) }
            .collect {
                searchData.postValue(it)
            }
    }
}
