package org.example.project.data.repository

import org.example.project.model.ApiResult
import org.example.project.model.News

class GetNewsRepository : BaseApiRepository() {

    suspend fun getNews(page:Int, pageSize:Int): ApiResult<List<News>> {
        return remote.getNews()
    }
}