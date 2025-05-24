package org.example.project.data.repository

import org.example.project.model.ApiResult
import org.example.project.model.News

interface GetNewsRepository {
    suspend fun getNews(page: Int, pageSize: Int): ApiResult<List<News>>
}