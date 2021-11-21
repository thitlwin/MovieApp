package com.thit.movieapp.data.local

import androidx.room.*
import com.thit.movieapp.data.responses.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMovie(vararg movies: Movie)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieList(movieList: List<Movie>) : List<Long>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int) : Movie?

    @Query("SELECT * FROM movie WHERE is_popular = 1")
    suspend fun getPopularMovie() : List<Movie>

    @Update
    suspend fun updateMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)
}