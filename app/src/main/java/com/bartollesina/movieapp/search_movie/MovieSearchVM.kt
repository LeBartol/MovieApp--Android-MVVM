package com.bartollesina.movieapp.search_movie


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartollesina.movieapp.adapter.OnItemClickedListener
import com.bartollesina.movieapp.repository.MovieRepository
import com.bartollesina.movieapp.utils.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
class MovieSearchVM(private val movieRepository: MovieRepository) : OnItemClickedListener,
    ViewModel() {
    val searchData = MutableLiveData<List<MovieSingleUi>>()
    val openDetails = SingleLiveData<String>()
    private val channelText = BroadcastChannel<String>(1)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            observeText()
        }
    }

    fun sendSearch(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
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

    override fun onItemClick(id:String) {
        openDetails.value = id
    }

    override fun onSaveClick(id: String) {
        TODO("Not yet implemented")
    }
}
