package org.example.project.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusResult<T>(
    @SerialName("status")
    val status: String,
    @SerialName("message")
    val message: String? = null,
    @SerialName("data")
    val data: T,
){

    fun isSuccess() = status == "success"
}