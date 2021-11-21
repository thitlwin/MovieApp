package com.thit.movieapp.data.network

import com.thit.movieapp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class RemoteDataSource @Inject constructor() {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
    }

    fun <Api> buildApi(api: Class<Api>): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .also { client ->
                        if (BuildConfig.DEBUG) {
                            val logging = HttpLoggingInterceptor()
                            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                            client.addInterceptor(logging)
                        }
                    }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}