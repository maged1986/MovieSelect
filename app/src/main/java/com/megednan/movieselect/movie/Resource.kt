package com.mego.movieselect.model
sealed class Resource<T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(Status.SUCCESS,data)
    class Loading<T>(data: T? = null) : Resource<T>(Status.RUNNING,data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(Status.FAILED,data, message)
}

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED

}