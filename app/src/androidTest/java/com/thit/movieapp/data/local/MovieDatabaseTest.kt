package com.thit.movieapp.data.local

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.thit.movieapp.data.responses.Movie
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MovieDatabaseTest : TestCase() {
    private lateinit var movieDao: MovieDao
    private lateinit var database: MovieDatabase

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .build()
        movieDao = database.getMovieDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        database.close()
    }

    @Test
    fun writeAndReadMovie() = runBlocking {
        val movie = Movie(
            adult = true,
            backdrop_path = "",
            genre_ids = listOf(1, 2, 3),
            id = 1,
            original_language = "eng",
            original_title = "Bla Title",
            overview = "Overview String",
            popularity = 2.00,
            poster_path = "",
            release_date = "22/33/4434",
            title = "title",
            video = true,
            vote_average = 1.00,
            vote_count = 200,
            is_favorite = true,
            is_popular = true,
            up_coming_date = "44/23/434"
        )
        movieDao.insertMovie(movie)
        var movies = movieDao.getPopularMovies()
        Log.d("-tag--", "movies list=${movies.size}")
        assertThat(movies.contains(movie)).isTrue()
    }
}