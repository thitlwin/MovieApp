package com.thit.movieapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thit.movieapp.R
import com.thit.movieapp.data.responses.Movie
import com.thit.movieapp.databinding.MovieListItemBinding
import com.thit.movieapp.imageUrl
import com.thit.movieapp.ui.movie.MovieListFragmentDirections

class MovieAdapter(
    private var movies: MutableList<Movie>,
    private val onFavoriteCallback: (Movie) -> Unit
) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val TAG = javaClass.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Log.d(TAG,"--onBindViewHolder---,movies[position].is_favorite=${movies[position].is_favorite}")
        holder.movieTitle.text = movies[position].title

        Glide.with(holder.itemView.context)
            .load(imageUrl + movies[position].poster_path)
            .placeholder(R.drawable.ic_placeholder)
            .centerCrop()
            .into(holder.movieImage)

        holder.itemView.setOnClickListener {
            val action =
                MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movies[position])
//            action.movie = movies[position]
            Navigation.findNavController(it).navigate(action)
        }
        if (movies[position].is_favorite)
            holder.favoriteImage.setImageResource(R.drawable.ic_favorite)
        else
            holder.favoriteImage.setImageResource(R.drawable.ic_favorite_border)
        holder.favoriteImage.setOnClickListener {
            onFavoriteCallback(movies[position])
        }
    }

    override fun getItemCount(): Int = movies.size

    fun addData(results: MutableList<Movie>) {
        movies = results
    }

    fun editItem(it: Movie) {
        Log.d(TAG, "editItem - $it")
        val index = movies.indexOf(it)
//        movies.apply { movies[index].is_favorite = !movies[index].is_favorite }
        movies = movies.also { movies[index].is_favorite = !movies[index].is_favorite}
//        notifyItemRemoved(index)
        Log.d(TAG, "atEdit, movies[index].is_favorite - ${movies[index].is_favorite}")
        notifyItemChanged(index)
    }


    class MovieViewHolder(binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val movieTitle: TextView = binding.textViewMovieTitle
        val movieImage: ImageView = binding.imageView
        val favoriteImage: ImageView = binding.imageViewFavorite
    }
}
