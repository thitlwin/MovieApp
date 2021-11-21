package com.thit.movieapp.data.responses

data class UpcomingMovieResponse(
    val dates: Dates,
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

data class Dates(
    val maximum: String,
    val minimum: String
)