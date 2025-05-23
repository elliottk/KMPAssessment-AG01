package org.example.project.data.remote

import io.ktor.client.HttpClient

expect class KtorClientFactory() {

    fun createClient(): HttpClient
}