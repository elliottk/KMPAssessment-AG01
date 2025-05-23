package org.example.project.model

import org.example.project.data.remote.exception.RemoteException

sealed class ApiResult<T> {

    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error<T>(val error: RemoteException) : ApiResult<T>()
}