package org.example.project.data.repository

import org.example.project.data.remote.exception.RemoteException
import org.example.project.model.ApiResult
import org.example.project.model.News

/**
 * Repository for fetching news articles.
 *
 * This class handles the logic of retrieving news from a remote data source
 * and optionally caching it locally.
 */
class GetNewsRepository : BaseRepository() {

    /**
     * Fetches news articles for a given page and page size.
     *
     * This function first attempts to retrieve news from the remote data source.
     * If the remote call is successful and it's the first page, the local cache is cleared
     * before saving the new data.
     * If the remote call fails due to a connection error, it attempts to load data
     * from the local cache.
     *
     * @param page The page number to fetch.
     * @param pageSize The number of news articles per page.
     * @return An [ApiResult] containing a list of [News] articles on success,
     * or an error state on failure.
     */
    suspend fun getNews(page: Int, pageSize: Int): ApiResult<List<News>> {

        val offset = (page - 1) * pageSize

        return when (val remoteResult = remote.getNews()) {
            is ApiResult.Success -> {
                if (page == 1) {
                    database.clearCache()
                }
                database.saveNewsToCache(remoteResult.data)
                // Always return data from cache after a successful remote fetch
                val cachedResult = database.getNewsFromCache(offset, pageSize)
                ApiResult.Success(cachedResult)
            }

            is ApiResult.Error -> {
                if (remoteResult.error is RemoteException.ConnectionError) {
                    // Attempt to load from cache on connection error
                    val cachedResult = database.getNewsFromCache(offset, pageSize)
                    ApiResult.Success(cachedResult)
                } else {
                    remoteResult
                }
            }
        }
    }
}