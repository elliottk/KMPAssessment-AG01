package org.example.project.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

actual class KtorClientFactory {
    
    actual fun createClient(): HttpClient {
        return HttpClient(Android) {
        }
    }

}