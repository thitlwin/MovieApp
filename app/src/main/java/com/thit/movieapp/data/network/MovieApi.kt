package com.thit.movieapp.data.network

import com.thit.movieapp.data.responses.PopularMovieResponse
import com.thit.movieapp.data.responses.UpcomingMovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieApi {

    @GET("popular")
    suspend fun getPopularMovies(
        @Header("Authorization") apiKey: String,
        @Query("language") language: String? = "en-US"
    ): PopularMovieResponse

    @GET("upcoming")
    suspend fun getUpcomingMovies(
        @Header("Authorization") apiKey: String,
        @Query("language") language: String? = "en-US"
    ): UpcomingMovieResponse
}