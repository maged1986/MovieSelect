package com.megednan.movieselect.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.megednan.movieselect.R
import com.megednan.movieselect.databinding.RecommendationItemBinding
import com.megednan.movieselect.util.Constants

class RecommendMoviesAdapter: ListAdapter<com.megednan.movieselect.model.movie.Result, RecommendMoviesAdapter.RecommendMoviesHolder>(
    COMPARATOR
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendMoviesHolder {
        val binding = RecommendationItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return RecommendMoviesHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendMoviesHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
    }

    inner class RecommendMoviesHolder(val binding: RecommendationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: com.megednan.movieselect.model.movie.Result) {

            binding.apply {
                recItemTvTitle.text = result.title
                recItemTvDate.text = result.releaseDate
                val url: String = Constants.POSTER_BASE_URL + result.posterPath
                Glide.with(binding.root).load(url).centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.poster_placeholder)
                    .into(recItemIvPoster)

                recItemCv.setOnClickListener {
                    onItemClickListener?.let { it(result) }
                }
                recItemRbRate.rating=(result.voteAverage/2).toFloat()
            }
        }
    }


    private var onItemClickListener: ((com.megednan.movieselect.model.movie.Result) -> Unit)? = null

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<com.megednan.movieselect.model.movie.Result>() {
            override fun areItemsTheSame(oldItem: com.megednan.movieselect.model.movie.Result, newItem: com.megednan.movieselect.model.movie.Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: com.megednan.movieselect.model.movie.Result, newItem: com.megednan.movieselect.model.movie.Result): Boolean {
                return oldItem == newItem
            }

        }
    }

    fun setOnItemClickListener(listener: (com.megednan.movieselect.model.movie.Result) -> Unit) {
        onItemClickListener = listener
    }

}