package org.example.project.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class News(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("author")
    val author: String,
    @SerialName("isLocal")
    val isLocal: Boolean,
    @SerialName("publishedAtUnix")
    val publishedAtUnix: Long,
    @SerialName("media")
    val media: Media?
)