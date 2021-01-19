package com.megednan.movieselect.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.megednan.movieselect.R
import com.megednan.movieselect.databinding.MovieListItemBinding
import com.megednan.movieselect.model.movie.Movie
import com.megednan.movieselect.util.Constants

class MoviePagingAdapter (): PagingDataAdapter<Movie, MoviePagingAdapter.MovieItemViewHolder>(COMPARATOR) {


    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(it) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val binding = MovieListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return MovieItemViewHolder(binding)
    }

    private var onItemClickListener: ((Movie) -> Unit)? = null


    inner class MovieItemViewHolder(val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.apply {
                cvMovieTitle.text = movie.title
                cvMovieReleaseDate.text = movie.releaseDate
                val url: String = Constants.POSTER_BASE_URL + movie.posterPath
                Glide.with(binding.root).load(url).centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.poster_placeholder)
                    .into(cvIvMoviePoster)

                cardView.setOnClickListener {
                    onItemClickListener?.let { it(movie) }
                }
            }
        }

    }


    companion object{
        val COMPARATOR= object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem==newItem
            }

        }
    }
    fun setOnItemClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }
}