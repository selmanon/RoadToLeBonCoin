package com.technicaltest.roadtoleboncoin.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Empty : Result<Nothing>()

    object Loading : Result<Nothing>()

}
