package com.megednan.movieselect.viewmodels

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.megednan.movieselect.model.movie.MovieDetails
import com.megednan.movieselect.model.movie.Recommendations
import com.megednan.movieselect.model.movie.VideoResponse
import com.megednan.movieselect.repositories.MoviesRepository
import com.mego.movieselect.model.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MoviesDetailsViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val TAG = "MoviesDetailsViewModel"
    private val _movie = MutableLiveData<MovieDetails>()
    val movie: LiveData<MovieDetails>
        get() = _movie

    private val _recommendation =
        MutableLiveData<Resource<Recommendations>>()
    val recommendation: LiveData<Resource<Recommendations>>
        get() = _recommendation
    private val _video = MutableLiveData<VideoResponse>()
    val video: LiveData<VideoResponse>
        get() = _video

    private val movieCompositeDisposable = CompositeDisposable()
    private val recommendMovieCompositeDisposable = CompositeDisposable()
    private val videoMovieCompositeDisposable = CompositeDisposable()
    var id = savedStateHandle.get<Long>("MovieId")

    init {
        loadMovie(id!!)

    }

    fun loadMovie(id: Long) {
        movieCompositeDisposable.add(
            moviesRepository.getMovieDetails(id!!).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movieDetails ->
                    _movie.value = (movieDetails)
                }, { error ->
                    Log.d(TAG, "error: " + error.message.toString())

                })
        )
        loadRecommendation(id)
        loadVideo(id)
    }

    fun loadVideo(id: Long) {
        videoMovieCompositeDisposable.add(
            moviesRepository.getVideos(id!!).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ video ->
                    _video.value = video
                }, { error ->
                    Log.d(TAG, "error: " + error.message.toString())

                })
        )
    }
    fun loadRecommendation(id: Long) {
        recommendMovieCompositeDisposable.add(
            moviesRepository.getRecommendMovie(id!!).subscribeOn(Schedulers.computation())
                .doOnSubscribe {
                    _recommendation.value = Resource.Loading()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ recommendation ->
                    _recommendation.value =
                        Resource.Success(recommendation)
                }, { error ->
                    _recommendation.value = Resource.Error(error.message!!)
                })
        )
    }

    override fun onCleared() {
        movieCompositeDisposable.clear()
        recommendMovieCompositeDisposable.clear()
        super.onCleared()
    }
}