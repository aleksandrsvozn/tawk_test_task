package com.alexvoz.tawk_test_task.data.wrappers

/**
 *  Tawk_test_task Resource wrapper
 *
 *  @property Success
 *  @property Loading
 *  @property Error
 */
sealed class Resource<out T> {

    val getData: T?
        get() = when (this) {
            is Success -> data
            is Loading -> data
            is Error -> data
        }

    data class Success<out T>(val data: T) : Resource<T>()
    data class Loading<out T>(val data: T? = null) : Resource<T>()
    data class Error<out T>(val data: T? = null, val error: Throwable) : Resource<T>()
}




