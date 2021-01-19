package com.megednan.movieselect.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.megednan.movieselect.model.movie.Movie
import com.megednan.movieselect.model.movie.MovieDetails
import com.megednan.movieselect.model.movie.Recommendations
import com.megednan.movieselect.model.movie.VideoResponse
import com.megednan.movieselect.pagingDatasource.NowPlayingDataSource
import com.megednan.movieselect.pagingDatasource.PopularDataSource
import com.megednan.movieselect.pagingDatasource.TopRatingDataSource
import com.megednan.movieselect.service.MovieApi
import com.megednan.movieselect.util.Constants.API_KEY
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class MoviesRepository  @Inject constructor(
    private val apiService: MovieApi,
    private val popularDataSource: PopularDataSource,
    private val topRatingDataSource: TopRatingDataSource,
    private val nowPlayingDataSource: NowPlayingDataSource,


    ) {

    fun getPopularMovie(): Flowable<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 1

            ), pagingSourceFactory = { popularDataSource }

        ).flowable
    }

    fun getTopRatedMovie(): Flowable<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 1

            ), pagingSourceFactory = { topRatingDataSource }

        ).flowable
    }

    fun getNowPlayingMovie(): Flowable<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 1

            ), pagingSourceFactory = { nowPlayingDataSource }

        ).flowable
    }

    fun getMovieDetails(id: Long): Single<MovieDetails> {
        return apiService.getMovieDetails(id, API_KEY)
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
    }

    fun getRecommendMovie(id: Long): Single<Recommendations> {
        return apiService.getRecommendationDetails(id, API_KEY)
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
    }

    fun getVideos(id: Long): Single<VideoResponse> {
        return apiService.getVideos(id, API_KEY)
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
    }

}