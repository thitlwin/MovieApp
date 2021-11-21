package com.thit.movieapp.data.local

import androidx.room.*
import com.thit.movieapp.data.responses.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(vararg movies: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(movieList: List<Movie>) : List<Long>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int) : Movie?

    @Query("SELECT * FROM movie WHERE is_popular = 1")
    suspend fun getPopularMovies() : List<Movie>

    @Update
    suspend fun updateMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM movie WHERE up_coming_date <> null ")
    suspend fun getUpcomingMovies() : List<Movie>
}