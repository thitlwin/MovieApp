package com.thit.movieapp.ui.movie

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.thit.movieapp.R
import com.thit.movieapp.data.responses.Movie
import com.thit.movieapp.databinding.FragmentMovieDetailBinding
import com.thit.movieapp.imageUrl
import com.thit.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MovieViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailBinding.bind(view)
        val movie = args.movie

        bindMovieDataToView(movie)
        binding.buttonAddToFavorite.setOnClickListener {
            if (!movie.is_favorite){
                binding.buttonAddToFavorite.text = "Remove From Favorite"
            }
            else{
                binding
                    .buttonAddToFavorite.text = "Add To Favorite"
            }
            viewModel.toggleFavoriteStatus(movie)
        }

    }

    private fun bindMovieDataToView(movie: Movie) {
        (activity as AppCompatActivity).supportActionBar?.title = "Movie Detail"
        binding.movieTitle.text = movie?.title
        Glide.with(requireContext())
            .load(imageUrl + movie?.poster_path)
            .placeholder(R.drawable.ic_placeholder)
            .centerCrop()
            .into(binding.headerImage)
        binding.textViewOverview.text = movie?.overview
        binding.textViewAverageVote.text = "Average Voting = ${movie?.vote_average}"
        binding.textViewReleaseDate.text = "Release Date = ${movie?.release_date}"
        if (movie.is_favorite){
            binding.buttonAddToFavorite.text = "Remove From Favorite"
        }
        else{
            binding
                .buttonAddToFavorite.text = "Add To Favorite"
        }
    }
}