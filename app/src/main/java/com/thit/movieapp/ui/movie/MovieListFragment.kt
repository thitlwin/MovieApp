package com.thit.movieapp.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.thit.movieapp.R
import com.thit.movieapp.data.network.Resource
import com.thit.movieapp.data.responses.Movie
import com.thit.movieapp.databinding.FragmentMovieListBinding
import com.thit.movieapp.ui.adapter.MovieAdapter
import com.thit.movieapp.viewmodel.MovieViewModel
import com.thit.movieapp.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {
    private final val TAG = javaClass.simpleName
    private lateinit var adapterForPopularMovie: MovieAdapter
    private lateinit var adapterFoUpcomingMovie: MovieAdapter
    private lateinit var binding: FragmentMovieListBinding
    private val viewModel: MovieViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieListBinding.bind(view)
        binding.textViewPopularMovieTitle.visible(false)
        binding.textViewUpcomingTitle.visible(false)

        (activity as AppCompatActivity).supportActionBar?.title = "Movie List"
        setupRecyclerViewAdapterForPopularMovie()
        setupRecyclerViewAdapterForUpcomingMovie()
        observeMovieLists()
    }

    private fun observeMovieLists() {
        viewModel.popularMovieResponse.observe(this, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            binding.textViewPopularMovieTitle.visible(it is Resource.Success)
            binding.textViewUpcomingTitle.visible(it is Resource.Success)
            when (it) {
                is Resource.Success -> {
                    renderPopularMovie(it.value.results.toMutableList())
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Failure", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.upcomingMovieResponse.observe(this, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    renderUpcomingMovies(it.value.results.toMutableList())
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Failure", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun renderUpcomingMovies(results: MutableList<Movie>) {
        adapterFoUpcomingMovie.addData(results)
        adapterFoUpcomingMovie.notifyDataSetChanged()
    }

    private fun renderPopularMovie(results: MutableList<Movie>) {
        adapterForPopularMovie.addData(results)
        adapterForPopularMovie.notifyDataSetChanged()
    }

    private fun setupRecyclerViewAdapterForPopularMovie() {
        binding.recyclerViewForPopularMovie.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapterForPopularMovie = MovieAdapter(arrayListOf()) {
            viewModel.toggleFavoriteStatus(it)
            adapterForPopularMovie.editItem(it)
        }
        binding.recyclerViewForPopularMovie.adapter = adapterForPopularMovie
    }

    private fun setupRecyclerViewAdapterForUpcomingMovie() {
        binding.recyclerViewForUpcomingMovie.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapterFoUpcomingMovie = MovieAdapter(arrayListOf()){
            viewModel.toggleFavoriteStatus(it)
            adapterFoUpcomingMovie.editItem(it)
        }
        binding.recyclerViewForUpcomingMovie.adapter = adapterFoUpcomingMovie
    }
}

