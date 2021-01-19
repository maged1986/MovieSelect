package com.megednan.movieselect.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.megednan.movieselect.R
import com.megednan.movieselect.adapter.MovieFooterState
import com.megednan.movieselect.adapter.MoviePagingAdapter
import com.megednan.movieselect.databinding.PopularMoviesFragmentBinding
import com.megednan.movieselect.viewmodels.PopularMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.popular_movies_fragment.*
@AndroidEntryPoint
class PopularMoviesFragment : Fragment(R.layout.popular_movies_fragment) {
    private val viewModel by viewModels<PopularMoviesViewModel>()
    lateinit var adapter: MoviePagingAdapter
    lateinit var binding: PopularMoviesFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MoviePagingAdapter()
        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putLong("MovieId", it.id)
            }
            findNavController().navigate(
                    R.id.action_popularMoviesFragment_to_moviesDetailsFragment,
                    bundle
            )
        }
        val gridLayoutManager = GridLayoutManager(this.context, 3)
        binding = PopularMoviesFragmentBinding.bind(view)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = gridLayoutManager
            recyclerView.adapter = adapter.withLoadStateFooter(
                    MovieFooterState { adapter.retry() })
        }
        adapter.addLoadStateListener{ loadStatus->
            binding.srl.isRefreshing=loadStatus.source.refresh is LoadState.Loading
            error_screen.isVisible=loadStatus.source.refresh is LoadState.Error
            binding.recyclerView.isVisible= !error_screen.isVisible

            if(loadStatus.source.refresh is LoadState.Error){
                error_btn.setOnClickListener {
                    adapter.retry()
                }
                error_screen.isVisible=loadStatus.source.refresh is LoadState.Error
                val errormessage=(loadStatus.source.refresh as LoadState.Error).error.message
                error_tv.text=errormessage
            }

        }
        srl.setOnRefreshListener {
            viewModel.onRefresh()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.PopularMovies.observe(viewLifecycleOwner, Observer {
            adapter.submitData(lifecycle, it)
        })
    }
}