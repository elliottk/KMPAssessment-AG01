package org.example.project.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.serialization.json.Json
import org.example.project.data.remote.RemoteHelper
import org.example.project.data.remote.RemoteHelperImpl

/**
 * Dependency injection module providing application-wide singleton instances.
 *
 * This object serves as a simple service locator pattern implementation,
 * managing the creation and lifecycle of core application dependencies.
 * All dependencies are lazily initialized to improve application startup
 * performance and ensure they're only created when actually needed.
 *
 * The module centralizes dependency configuration and provides a single
 * source of truth for shared instances across the application.
 */
object AppModule {

    /**
     * Lazy-initialized HTTP client for making network requests.
     *
     * Uses the CIO (Coroutine-based I/O) engine which is suitable for
     * multiplatform applications and provides good performance with
     * Kotlin coroutines integration.
     *
     * @see HttpClient for client configuration options
     * @see CIO for engine-specific details
     */
    val ktorClient by lazy { HttpClient(CIO) }

    /**
     * Lazy-initialized JSON serialization configuration.
     *
     * Configured with ignoreUnknownKeys = true to provide resilience
     * against API changes by ignoring unexpected fields in JSON responses.
     * This prevents parsing errors when the server adds new fields.
     *
     * @see Json for additional configuration options
     */
    val json by lazy {
        Json {
            ignoreUnknownKeys = true
        }
    }

    /**
     * Lazy-initialized remote data access helper.
     *
     * Provides the main interface for all remote API operations.
     * Uses the concrete RemoteHelperImpl implementation which leverages
     * the shared ktorClient and json instances for consistency.
     *
     * @see RemoteHelper for the interface contract
     * @see RemoteHelperImpl for the concrete implementation
     */
    val remoteHelper: RemoteHelper by lazy { RemoteHelperImpl() }
}