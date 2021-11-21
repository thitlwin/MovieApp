package com.thit.movieapp.data.responses

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.thit.movieapp.data.local.converter.FieldTypeConverter
import java.io.Serializable

@Entity
data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    @TypeConverters(FieldTypeConverter::class)
    val genre_ids: List<Int>,
    @PrimaryKey
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    var is_favorite: Boolean,
    var is_popular: Boolean,
    var up_coming_date: String?
) : Serializable