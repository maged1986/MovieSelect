package com.megednan.movieselect.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.megednan.movieselect.model.movie.Movie
import com.megednan.movieselect.repositories.MoviesRepository
import io.reactivex.disposables.CompositeDisposable

class PopularMoviesViewModel@ViewModelInject constructor(
    private val movieRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private  val TAG = "MovieViewmodel"
    private val compositeDisposable= CompositeDisposable()
    private val _PopularMovies= MutableLiveData<PagingData<Movie>>()
    val PopularMovies: LiveData<PagingData<Movie>>
        get() = _PopularMovies
    private val _movie= MutableLiveData<List<Movie>>()
    val movie: LiveData<List<Movie>>
        get()=_movie

    init {
        compositeDisposable.add(
            movieRepository.getPopularMovie().cachedIn(viewModelScope)
                .subscribe({
                    _PopularMovies.value=it
                })
        )
    }
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
    fun onRefresh() {
        compositeDisposable.add(
            movieRepository.getPopularMovie().cachedIn(viewModelScope)
                .subscribe({
                    _PopularMovies.value=it
                })
        )}
}

