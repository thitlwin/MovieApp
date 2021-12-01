package com.thit.movieapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.thit.movieapp.apiKey
import com.thit.movieapp.data.UserPreferences
import com.thit.movieapp.data.local.MovieDao
import com.thit.movieapp.data.network.MovieApi
import com.thit.movieapp.data.responses.Movie
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: MovieApi,
    private val movieDao: MovieDao,
    private val userPreferences: UserPreferences
) : BaseRepository() {
    private val TAG = javaClass.simpleName

    suspend fun getPopularMovies() = safeApiCall {
//        Log.i(TAG, "getPopularMovies----")

        api.getPopularMovies(apiKey = apiKey)
            .also {
                it.results.map { it -> it.is_popular = true }
                movieDao.insertMovieList(it.results)
            }
    }

    suspend fun getUpcomingMovies() = safeApiCall {
//        Log.i(TAG, "getUpcomingMovies----")

        api.getUpcomingMovies(apiKey = apiKey)
            .also {
                var upcomingDateRange = it.dates.minimum + ":" + it.dates.maximum
                it.results.map { it -> it.up_coming_date = upcomingDateRange }
                movieDao.insertMovieList(it.results)
            }
    }

    fun getPopularMoviesFromDB(): LiveData<List<Movie>> {
//        Log.i(TAG, "getPopularMoviesFromDB")
        return movieDao.getPopularMovies()
    }

    fun getUpcomingMoviesFromDB(): LiveData<List<Movie>> {
//        Log.i(TAG, "getUpcomingMoviesFromDB")
        return movieDao.getUpcomingMovies()
    }

    suspend fun saveMoveToDB(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    suspend fun updateMovie(movie: Movie) {
        return movieDao.updateMovie(movie)
    }

    suspend fun getMovieById(movieId: Int): Movie? {
        return movieDao.getMovieById(movieId)
    }

    suspend fun deleteMovieFromDB(movie: Movie) {
        movieDao.deleteMovie(movie)
    }
}