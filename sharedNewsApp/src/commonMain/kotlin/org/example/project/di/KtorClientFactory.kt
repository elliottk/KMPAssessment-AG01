package org.example.project.di

import io.ktor.client.HttpClient

expect class KtorClientFactory() {

    fun createClient(): HttpClient
}