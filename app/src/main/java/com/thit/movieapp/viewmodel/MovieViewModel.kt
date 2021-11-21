package com.thit.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.thit.movieapp.data.network.Resource
import com.thit.movieapp.data.repository.MovieRepository
import com.thit.movieapp.data.responses.Movie
import com.thit.movieapp.data.responses.PopularMovieResponse
import com.thit.movieapp.data.responses.UpcomingMovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private final val TAG = javaClass.simpleName
    private val _popularMovieResponse: MutableLiveData<Resource<PopularMovieResponse>> =
        MutableLiveData()
    val popularMovieResponse: LiveData<Resource<PopularMovieResponse>>
        get() = _popularMovieResponse

    private val _upcomingMovieResponse: MutableLiveData<Resource<UpcomingMovieResponse>> =
        MutableLiveData()
    val upcomingMovieResponse: LiveData<Resource<UpcomingMovieResponse>>
        get() = _upcomingMovieResponse

    init {
        loadMovies()
    }

    private fun getPopularMovies() = viewModelScope.launch {
        _popularMovieResponse.value = repository.getPopularMovies()
    }

    private fun getUpcomingMovies() = viewModelScope.launch {
        _upcomingMovieResponse.value = repository.getUpcomingMovies()
    }

    fun toggleFavoriteStatus(movie: Movie) = viewModelScope.launch {
        movie.is_favorite = !movie.is_favorite
        repository.updateMovie(movie)
    }

    fun loadMovies() {
        getPopularMovies()
        getUpcomingMovies()
    }
}