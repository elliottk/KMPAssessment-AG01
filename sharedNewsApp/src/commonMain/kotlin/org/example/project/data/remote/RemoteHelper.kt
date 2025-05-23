package org.example.project.data.remote

import org.example.project.model.ApiResult
import org.example.project.model.News

/**
 * Interface defining remote data access operations for the application.
 *
 * This interface abstracts the remote data layer, providing a contract
 * for fetching data from external APIs. It follows the Repository pattern
 * to separate the data access logic from business logic.
 *
 * All methods return ApiResult to provide consistent error handling
 * and success/error states throughout the application.
 */
interface RemoteHelper {

    /**
     * Fetches the news articles from the remote API.
     *
     * @return ApiResult<List<News>> containing either:
     *         - Success with a list of News objects
     *         - Error with appropriate RemoteException details
     */
    suspend fun getNews():ApiResult<List<News>>
}