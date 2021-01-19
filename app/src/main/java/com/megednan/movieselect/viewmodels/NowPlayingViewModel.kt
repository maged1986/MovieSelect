package com.megednan.movieselect.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.megednan.movieselect.model.movie.Movie
import com.megednan.movieselect.repositories.MoviesRepository
import io.reactivex.disposables.CompositeDisposable

class NowPlayingViewModel @ViewModelInject constructor(
    private val movieRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private  val TAG = "MovieViewmodel"
    private val compositeDisposable= CompositeDisposable()
    private val _NowPlayingMovies= MutableLiveData<PagingData<Movie>>()
    val NowPlayingMovies: LiveData<PagingData<Movie>>
        get() = _NowPlayingMovies
    private val _movie= MutableLiveData<List<Movie>>()
    val movie: LiveData<List<Movie>>
        get()=_movie

    init {
        compositeDisposable.add(
            movieRepository.getNowPlayingMovie().cachedIn(viewModelScope)
                .subscribe({
                    _NowPlayingMovies.value=it
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun onRefresh() {
        compositeDisposable.add(
            movieRepository.getNowPlayingMovie().cachedIn(viewModelScope)
                .subscribe({
                    _NowPlayingMovies.value=it
                })
        )}

}