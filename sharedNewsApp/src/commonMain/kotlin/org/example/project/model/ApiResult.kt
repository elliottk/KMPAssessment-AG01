package org.example.project.model

import org.example.project.data.remote.exception.RemoteException

/**
 * A sealed class representing the result of an API operation.
 * It can either be a [Success] containing the fetched data, or an [Error]
 * containing a [RemoteException] if the operation failed.
 *
 * This class is generic, allowing it to be used for various types of data
 * returned by API calls.
 *
 * Using a sealed class ensures that all possible outcomes of an API call
 * are explicitly handled, promoting robust error management.
 *
 * @param T The type of data expected in a successful API response.
 */
sealed class ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error<T>(val error: RemoteException) : ApiResult<T>()
}