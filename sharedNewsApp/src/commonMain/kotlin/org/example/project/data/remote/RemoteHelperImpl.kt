package org.example.project.data.remote

import org.example.project.model.ApiResult
import org.example.project.model.News

/**
 * Concrete implementation of RemoteHelper interface.
 *
 * This class extends BaseRemoteHelper to inherit common HTTP request functionality
 * and implements the RemoteHelper interface to provide specific API operations.
 * It serves as the main entry point for all remote data operations in the application.
 *
 * The implementation uses predefined API endpoints and leverages the base class
 * error handling and JSON parsing capabilities.
 *
 * @see BaseRemoteHelper for inherited HTTP functionality
 * @see RemoteHelper for the interface contract
 * @see ApiEndPoint for endpoint definitions
 */
class RemoteHelperImpl : BaseRemoteHelper(), RemoteHelper {

    override suspend fun getNews(): ApiResult<List<News>> {
        return call(ApiEndPoint.GET_NEWS)
    }

}