package com.thit.movieapp.ui.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thit.movieapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
    }
}