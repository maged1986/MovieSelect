package com.megednan.movieselect.pagingDatasource

import androidx.paging.rxjava2.RxPagingSource
import com.megednan.movieselect.model.movie.Movie
import com.megednan.movieselect.service.MovieApi
import com.megednan.movieselect.util.Constants
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NowPlayingDataSource  @Inject constructor(
    private val movieApi: MovieApi
): RxPagingSource<Int, Movie>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
        val page:Int=params.key?:1
        return movieApi.getNowPlayingMovie(Constants.API_KEY,page).subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    data = it.movieList,
                    prevKey = if (page == 1)null else page-1,
                    nextKey = if (page == it.totalPages) null else it.page+1
                )as LoadResult<Int, Movie>
            }
            .onErrorReturn { LoadResult.Error(it) }
    }


}