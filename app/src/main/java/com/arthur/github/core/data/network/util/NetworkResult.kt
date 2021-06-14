package com.arthur.github.core.data.network.util

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class NetworkResult<out T> {

    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String, val code: Int? = null) : NetworkResult<Nothing>()
    object NetworkError : NetworkResult<Nothing>()
    object CancellationError : NetworkResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<T> -> "Success[data=$data]"
            is Error -> "Error[exception=$message]"
            NetworkError -> "Network Error"
            CancellationError -> "Cancellation Error"
        }
    }
}

inline fun <T> NetworkResult<T>.onSuccess(
    crossinline callback: (value: T) -> Unit
): NetworkResult<T> {
    if (this is NetworkResult.Success) {
        callback((this).data)
    }
    return this
}

inline fun <T> NetworkResult<T>.onError(
    crossinline callback: (value: NetworkResult.Error) -> Unit
): NetworkResult<T> {
    if (this is NetworkResult.Error) {
        callback(this)
    }
    return this
}