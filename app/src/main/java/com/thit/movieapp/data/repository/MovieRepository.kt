package com.thit.movieapp.data.repository

import android.util.Log
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
        api.getPopularMovies(apiKey = apiKey).also {
            it.results.map { it.is_popular = true }
            movieDao.insertMovieList(it.results)
        }
    }

    suspend fun getPopularMoviesFromDB() {
        Log.i(TAG, "getPopularMoviesFromDB")
        movieDao.getPopularMovie()
    }

    suspend fun getUpcomingMovies() = safeApiCall {
        api.getUpcomingMovies(apiKey = apiKey).apply {
            movieDao.insertMovieList(this.results)
        }
    }

    suspend fun saveMoveToDB(movie: Movie) {
        Log.i(TAG, "saveMoveToDB, $movie")
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