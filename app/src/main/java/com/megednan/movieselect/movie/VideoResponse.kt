package com.megednan.movieselect.model.movie


import com.google.gson.annotations.SerializedName

data class VideoResponse(
    val id: Int,
    val results: List<ResultX>
)