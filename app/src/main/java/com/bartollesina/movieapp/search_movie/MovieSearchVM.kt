package com.bartollesina.movieapp.search_movie


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartollesina.movieapp.adapter.OnItemClickedListener
import com.bartollesina.movieapp.favorite_movies.mapFromSingleUiToEntity
import com.bartollesina.movieapp.repository.MovieRepository
import com.bartollesina.movieapp.utils.ResourceProvider
import com.bartollesina.movieapp.utils.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
class MovieSearchVM(
    private val movieRepository: MovieRepository,
    private val resourceProvider: ResourceProvider
) : OnItemClickedListener, ViewModel() {

    val searchData = MutableLiveData<List<MovieSingleUi>>()
    val openDetails = SingleLiveData<String>()
    val loader = SingleLiveData<Boolean>()
    private val channelText = MutableSharedFlow<String>(1)
    private var lastSearch = ""

    init {
        viewModelScope.launch(Dispatchers.IO) {
            observeText()
        }
        //for first opening of screen
        searchData.postValue(listOf(getEmptyState(lastSearch, resourceProvider)))
    }

    fun sendSearch(text: String) {
        loader.postValue(true)
        searchData.postValue(listOf())
        lastSearch = text
        viewModelScope.launch(Dispatchers.IO) {
            channelText.tryEmit(text)
        }
    }

    private suspend fun observeText() {
        channelText
            .debounce(300)
            .map { Pair(movieRepository.getMoviesSearch(it), movieRepository.getAllMovies()) }
            .map { mapFromSearchToUi(it.first, it.second, resourceProvider, lastSearch) }
            .collect {
                searchData.postValue(it)
                loader.postValue(false)
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
