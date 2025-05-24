package org.example.project.data.database

import org.example.project.model.News

interface DatabaseHelper {
    suspend fun getNewsFromCache(offset: Int,limit: Int):List<News>
    suspend fun saveNewsToCache(news: List<News>)
    suspend fun clearCache()
}