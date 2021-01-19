package com.megednan.movieselect.model.movie


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
    val id: Long,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
):Serializable