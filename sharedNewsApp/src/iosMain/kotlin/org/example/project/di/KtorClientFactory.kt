package org.example.project.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.HttpTimeout

actual class KtorClientFactory {

    actual fun createClient(): HttpClient {
        return HttpClient(Darwin) {

            install(HttpTimeout) {
                requestTimeoutMillis = 40000
            }
        }
    }

}