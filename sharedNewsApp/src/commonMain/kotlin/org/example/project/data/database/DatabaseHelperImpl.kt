package org.example.project.data.database

import org.example.project.di.AppModule
import org.example.project.model.News
import org.example.project.util.DatabaseMapper

class DatabaseHelperImpl : BaseDatabaseHelper(), DatabaseHelper {

    override suspend fun getNewsFromCache(offset: Int,limit: Int): List<News> {
        return database.newsQueries.getNews(offset.toLong(), limit.toLong(),DatabaseMapper.newsMapper).executeAsList()
    }

    override suspend fun saveNewsToCache(news: List<News>) {
        news.forEach {
            database.newsQueries.insertOrReplaceNews(
                id = it.id.toLong(),
                title = it.title,
                description = it.description,
                author = it.author,
                isLocal = if (it.isLocal) 1 else 0,
                publishedAtUnix = it.publishedAtUnix,
                media = if (it.media == null) null else AppModule.json.encodeToString(it.media)
            ).await()
        }
    }

    override suspend fun clearCache() {
        database.newsQueries.deleteAllNews().await()
    }
}