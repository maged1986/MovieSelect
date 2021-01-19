package com.megednan.movieselect.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.megednan.movieselect.R
import com.megednan.movieselect.adapter.RecommendMoviesAdapter
import com.megednan.movieselect.databinding.MoviesDetailsFragmentBinding
import com.megednan.movieselect.util.Constants.POSTER_BASE_URL
import com.megednan.movieselect.util.Constants.VIDEO_BASE_URL
import com.megednan.movieselect.viewmodels.MoviesDetailsViewModel
import com.mego.movieselect.model.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesDetailsFragment : Fragment(R.layout.movies_details_fragment) {

    private val viewModel by viewModels<MoviesDetailsViewModel>()
    private lateinit var binding: MoviesDetailsFragmentBinding
    private lateinit var movieAdapter: RecommendMoviesAdapter
    private val TAG = "MoviesDetailsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MoviesDetailsFragmentBinding.bind(view)
        movieAdapter = RecommendMoviesAdapter()
        val  layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvRecommendations.adapter = movieAdapter
        binding.rvRecommendations.layoutManager=layoutManager

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.movie.observe(viewLifecycleOwner, Observer {

            val url = POSTER_BASE_URL + it!!.posterPath
            Glide.with(this).load(url).centerCrop().into(binding.detailsIvPoster)
            Glide.with(this).load(url).fitCenter().into(binding.scrollIvMoviePoster)
            binding.apply {
                detailsTvGender.text = it.tagline
                detailsTvOverview.text = it.overview
                detailsTvReleaseDate.text = it.releaseDate
                detailsTvTitle.text = it.title
                detailsTvRuntime.text = it.runtime.toString()
                it.voteAverage.let { rating ->
                    detailsRating.rating = (rating / 2).toFloat()
                }
            }
        })
        viewModel.recommendation.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.RUNNING -> {
                    showLoading(true)
                }
                Status.SUCCESS -> {
                    showLoading(false)
                    val castList = it.data?.results
                    //   Log.d(TAG, "onActivityCreated: " + castList?.size)
                    movieAdapter.submitList(castList)
                    movieAdapter.setOnItemClickListener { viewModel.loadMovie(it.id.toLong()) }
                }
                Status.FAILED -> {
                    showLoading(false)
                    Snackbar.make(requireView(), it.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        })
        viewModel.video.observe(viewLifecycleOwner, Observer {
            val videoKey = it.results?.get(0)?.key
            val videoLink = VIDEO_BASE_URL + videoKey
            binding.detailsCvTrailer.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("videoLink", videoLink)
                }
               findNavController().navigate(
                    R.id.action_moviesDetailsFragment_to_videoFragment,
                    bundle
                )
            }
        })
    }


    private fun showLoading(isShow: Boolean) {
        if (isShow) {
            binding.moviesDetialsProgressbar.visibility = View.VISIBLE
        } else {
            binding.moviesDetialsProgressbar.visibility = View.GONE
        }


    }

}