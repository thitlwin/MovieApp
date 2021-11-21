package com.thit.movieapp.viewmodel

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

    private val _popularMovieList: MutableLiveData<List<Movie>> =
        MutableLiveData()
    val popularMovieList: LiveData<List<Movie>>
        get() = _popularMovieList

    private val _upcomingMovieList: MutableLiveData<List<Movie>> =
        MutableLiveData()
    val upcomingMovieList: LiveData<List<Movie>>
        get() = _upcomingMovieList

    init {
        checkMovieFromDBAndLoadFromApi()
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
        viewModelScope.launch {
            _popularMovieResponse.value = repository.getPopularMovies()
            _upcomingMovieResponse.value = repository.getUpcomingMovies()
        }
    }

    fun checkMovieFromDBAndLoadFromApi() {

        viewModelScope.launch {
            val upcomingList = repository.getUpcomingMoviesFromDB()
            if (upcomingList.isEmpty())
                getUpcomingMovies()
            else
                _upcomingMovieList.value = upcomingList

            val popularList = repository.getPopularMoviesFromDB()
            if (popularList.isEmpty())
                getPopularMovies()
            else
                _popularMovieList.value = popularList
        }

    }
}