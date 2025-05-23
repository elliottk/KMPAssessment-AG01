package org.example.project.data.remote

import org.example.project.data.remote.exception.RemoteException
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import org.example.project.di.AppModule
import org.example.project.model.ApiResult
import org.example.project.model.StatusResult

/**
 * Base helper class for handling remote API calls using Ktor client.
 *
 * This class provides a common foundation for making HTTP requests and handling
 * responses with proper error handling and JSON parsing. It encapsulates the
 * standard flow of:
 * 1. Making HTTP GET requests
 * 2. Handling network and server errors
 * 3. Parsing JSON responses into typed objects
 * 4. Wrapping results in ApiResult sealed class
 *
 * @see ApiResult for the result wrapper type
 * @see RemoteException for possible error types
 */
open class BaseRemoteHelper {

    /**
     * Makes an HTTP GET request to the specified URL and parses the response.
     *
     * This method handles the complete request lifecycle including:
     * - Network connection management
     * - HTTP status code validation
     * - JSON deserialization
     * - Error wrapping and propagation
     *
     * @param T The expected type of the response data
     * @param url The complete URL to make the request to
     * @return ApiResult<T> containing either success data or error information
     *
     * All exceptions are wrapped in ApiResult.Error
     */
    internal suspend inline fun <reified T> call(url: String): ApiResult<T> {

        val response: HttpResponse = try {
            AppModule.ktorClient.get {
                url(url)
            }
        } catch (e: Exception) {
            return ApiResult.Error(RemoteException.ConnectionError)
        }

        if (response.status.value == 500)
            return ApiResult.Error(RemoteException.ServerError)

        return try {
            val statusResult = AppModule.json.decodeFromString<StatusResult<T>>(response.bodyAsText())
            if (statusResult.isSuccess())
                ApiResult.Success(statusResult.data)
            else
                ApiResult.Error(RemoteException.ResultError(statusResult.message))
        } catch (e: Exception) {
            ApiResult.Error(RemoteException.ParseError)
        }

    }
}