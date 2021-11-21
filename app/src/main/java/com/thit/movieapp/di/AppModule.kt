package com.thit.movieapp.di
import android.content.Context
import androidx.room.Room
import com.thit.movieapp.data.local.MovieDao
import com.thit.movieapp.data.local.MovieDatabase
import com.thit.movieapp.data.network.MovieApi
import com.thit.movieapp.data.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMovieApi(remoteDataSource: RemoteDataSource) : MovieApi {
        return remoteDataSource.buildApi(MovieApi::class.java)
    }

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.getMovieDao()
    }

    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "movie_db").build()
    }
}