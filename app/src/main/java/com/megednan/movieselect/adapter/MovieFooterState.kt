package com.megednan.movieselect.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.megednan.movieselect.databinding.MoiveLoadingBinding

class MovieFooterState (private val retry: () -> Unit) :
    LoadStateAdapter<MovieFooterState.LoadingViewHolder>() {

    override fun onBindViewHolder(holder: LoadingViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadingViewHolder {
        val binding =
            MoiveLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingViewHolder(binding)
    }

    inner class LoadingViewHolder constructor(
        val binding: MoiveLoadingBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.button.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                if (loadState is LoadState.Error) {
                    textView.text = loadState.error.message
                    textView.isVisible = loadState is LoadState.Error
                    button.isVisible = loadState is LoadState.Error
                }
                progressBar.isVisible = loadState is LoadState.Loading
                textViewLoading.isVisible = loadState is LoadState.Loading


            }
        }
    }

}