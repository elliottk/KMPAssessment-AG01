package org.example.project.util

import org.example.project.di.AppModule
import org.example.project.model.News

object DatabaseMapper {

    val newsMapper: (
        id: Long,
        title: String,
        description: String,
        author: String,
        isLocal: Long,
        publishedAtUnix: Long,
        media: String?,
    ) -> News =
        { id, title, description, author, isLocal, publishedAtUnix, media ->
            News(
                id.toInt(), title, description, author, isLocal == 1L, publishedAtUnix, try {
                    AppModule.json.decodeFromString(media!!)
                } catch (ignore: Exception) {
                    null
                }
            )
        }

}