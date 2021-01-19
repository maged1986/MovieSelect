package com.megednan.movieselect.service

import com.megednan.movieselect.model.movie.MovieDetails
import com.megednan.movieselect.model.movie.MovieResponse
import com.megednan.movieselect.model.movie.Recommendations
import com.megednan.movieselect.model.movie.VideoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    fun getPopularMovie(
        @Query("api_key") api_key:String,
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovie(
        @Query("api_key") api_key:String,
        @Query("page") page: Int
    ): Single<MovieResponse>


    @GET("movie/top_rated")
    fun getTopRatedMovie(
        @Query("api_key") api_key:String,
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") id: Long,
        @Query("api_key") api_key:String,
    ): Single<MovieDetails>
    @GET("movie/{movie_id}/recommendations")
    fun getRecommendationDetails(
        @Path("movie_id") id: Long,
        @Query("api_key") api_key:String,
    ): Single<Recommendations>
    @GET("movie/{movie_id}/videos")
    fun getVideos(
        @Path("movie_id") id: Long,
        @Query("api_key") api_key:String,
    ): Single<VideoResponse>

}