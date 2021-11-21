package com.thit.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thit.movieapp.data.local.converter.FieldTypeConverter
import com.thit.movieapp.data.responses.Movie

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(FieldTypeConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao
}