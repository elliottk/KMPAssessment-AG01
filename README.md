# NewsApp

## Introduction

Welcome to NewsApp! A mobile application designed to fetch, display, and cache news articles, featuring support for pagination and a user-friendly night mode.

## Architectural Choices

We've made specific architectural decisions to ensure NewsApp is scalable, maintainable, and robust.

### Core Architecture Pattern

*   **Pattern:** MVI (Model-View-Intent)
*   **Reasoning:** We chose MVI to establish a unidirectional data flow and manage state predictably. This pattern helps in creating a clear separation of concerns, making the application easier to debug, test, and reason about, especially as the complexity of features like pagination and real-time updates grows. It also aligns well with reactive programming paradigms commonly used with Jetpack Compose.

### Key Technologies & Libraries

*   **Jetpack Compose Multiplatform (for UI)**:
    *   **Reasoning:** Jetpack Compose Multiplatform allows us to build the user interface for both Android and potentially other platforms (like Desktop or iOS in the future with KMM) using a single, declarative Kotlin codebase.
*   **Ktor (for Networking)**:
    *   **Reasoning:** Ktor is a powerful and flexible Kotlin-first asynchronous HTTP client that works seamlessly in a multiplatform environment. Its lightweight nature, coroutine integration, and ease of configuration make it an excellent choice for handling all network requests to fetch news articles.
*   **SQLDelight (for Local Database)**:
    *   **Reasoning:** SQLDelight provides a type-safe way to interact with local databases. It generates Kotlin APIs directly from SQL statements, offering compile-time checks and making database operations more reliable and easier to manage. This is for caching news data and supporting offline access with pagination.
*   **Kotlinx Serialization (for JSON parsing)**:
    *   **Reasoning:** Kotlinx Serialization is the official Kotlin library for JSON parsing and serialization. It integrates seamlessly with Kotlin and Ktor, providing efficient and type-safe conversion between JSON data from the news APIs and our Kotlin data classes.
*   **Dependency Management (AppModule - Service Locator)**:
    *   **Reasoning:** We use a simple service locator pattern implemented in `AppModule` for managing dependencies. This provides a centralized and straightforward way to access shared instances like the Ktor client, SQLDelight database, and other helpers. Dependencies are typically lazy-initialized for better startup performance.

### Important Technical Insights

*   **Shared Code (`shared` module):** The `shared` Kotlin Multiplatform module is the core of our application. It contains:
    *   Business logic for fetching and processing news.
    *   Data sources, including remote API calls via Ktor and local database interactions via SQLDelight.
    *   Repository patterns to abstract data access.
    *   MVI components: Models, Intents, and state management logic.
    *   Shared ViewModels/Presenters that drive the UI.
*   **Platform-Specific Code (`androidApp`,  `iosApp`):** The platform-specific modules (e.g., `androidApp`) are responsible for:
    *   Rendering the UI using Jetpack Compose (or platform-native UI toolkits if extended).
    *   Implementing any platform-specific services or APIs (e.g., specific device features, UI theme application for night mode).
    *   Connecting the shared ViewModels/Presenters to the platform's UI framework.
*   **Error Handling:** We utilize a sealed class, typically `ApiResult` or a similar construct, to represent the outcomes of network operations (and potentially other fallible operations). This ensures that both success and error states (e.g., network failures, API errors) are handled explicitly and robustly throughout the application, contributing to a more stable user experience.
*   **Asynchronous Operations:** Kotlin Coroutines are used extensively for managing asynchronous operations such as network requests, database access, and complex data transformations. This simplifies background task management, improves UI responsiveness by keeping the main thread unblocked, and makes asynchronous code easier to read and maintain.
*   **Pagination:** The app implements pagination to efficiently load and display large sets of news articles. This involves fetching data in chunks from the API and appending it to the local cache and UI list as the user scrolls, optimizing performance and data usage.
*   **Night Mode:** The UI supports a night mode (dark theme) to enhance user comfort in low-light conditions. This is typically handled through theming capabilities within Jetpack Compose, applying different color palettes based on user preference.

### Prerequisites

*   **Android Studio:** 
*   **SQLDelight Plugin:** You **must** install the SQLDelight plugin in Android Studio for schema generation and IDE support. Go to `Settings > Plugins`, search for "SQLDelight", and install it.
*   **Xcode (Optional - if developing for iOS):**