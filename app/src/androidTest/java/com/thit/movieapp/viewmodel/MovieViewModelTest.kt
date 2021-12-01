package com.thit.movieapp.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.thit.movieapp.data.UserPreferences
import com.thit.movieapp.data.local.MovieDatabase
import com.thit.movieapp.data.network.MovieApi
import com.thit.movieapp.data.network.RemoteDataSource
import com.thit.movieapp.data.repository.MovieRepository
import com.thit.movieapp.data.responses.Movie
import com.thit.movieapp.getOrAwaitValue
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class MovieViewModelTest : TestCase() {
    private lateinit var repository: MovieRepository
    private lateinit var viewModel: MovieViewModel
    private lateinit var database: MovieDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val remoteDataSource = RemoteDataSource()
        val movieApi = remoteDataSource.buildApi(MovieApi::class.java)
        database = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .allowMainThreadQueries().build()
        val movieDao = database.getMovieDao()
        repository = MovieRepository(movieApi, movieDao, UserPreferences(context))
        viewModel = MovieViewModel(repository)
    }

    @After
    @Throws(IOException::class)
    fun closeDB(){
        database.close()
    }

    @Test
    fun testAddingPopularMovie() = runBlocking{
//        val movie = Movie(
//            adult = true,
//            backdrop_path = "",
//            genre_ids = listOf(1, 2, 3, 4),
//            id = 2,
//            original_language = "eng",
//            original_title = "Bla Title 2",
//            overview = "Overview String 2",
//            popularity = 2.00,
//            poster_path = "",
//            release_date = "22/33/4434",
//            title = "title",
//            video = true,
//            vote_average = 1.00,
//            vote_count = 200,
//            is_favorite = true,
//            is_popular = true,
//            up_coming_date = "44/23/434"
//        )
//        repository.saveMoveToDB(movie)
        viewModel.loadMovies()
        val result = viewModel.popularMovieList.getOrAwaitValue()
        assertThat(result != null).isTrue()
    }
}