# NewsApp – Compose Multiplatform News Component

## Overview

**NewsApp** is a reusable, cross-platform UI component built with **Jetpack Compose Multiplatform (CMP)**.  
It is designed to be integrated into Android and iOS applications to display a paginated list of news stories.  
Each item includes a **headline**, **image**, and **publish date**.

This is **not a standalone application**, but a self-contained component that can be embedded into larger mobile projects.  
The module supports **pagination**, **offline access via SQLDelight caching**, **configurable page size**, and **dark mode support on Android**.

The focus of this implementation was to build a clean, testable, and scalable architecture using **Kotlin Multiplatform technologies** that demonstrate modern mobile development principles.

---

## Architectural Choices

### Model-View-Intent (MVI)

I chose the **MVI (Model-View-Intent)** pattern to enforce a **unidirectional data flow** and predictable state management.  
This approach helps separate concerns clearly between UI, logic, and data, making the module easier to extend and maintain.

My MVI structure includes:

- **Intent** – user-triggered actions like `loadNews()` or `refresh()`
- **State** – represented by the `NewsUiState` data class
- **ViewModel** – contains business logic, handles repository calls, and exposes `StateFlow<NewsUiState>` to the UI

The MVI design simplifies handling pagination, loading indicators, error messaging, and UI updates within Compose.

---

## Technology Stack

To ensure cross-platform support and maintainable architecture, the following technologies were used:

- **Jetpack Compose Multiplatform** – shared UI code for Android and iOS
- **Ktor Client** – for HTTP requests to the news endpoint  
  `https://cbcmusic.github.io/assessment-tmp/data/data.json`
- **Kotlinx Serialization** – for type-safe JSON deserialization
- **SQLDelight** – for offline caching with compile-time-safe SQL
- **Kotlin Coroutines** – for asynchronous tasks like pagination, API calls, and database access
- **Mokkery + kotlinx.coroutines.test** – for unit testing across platforms

---

## Shared Module Structure

All logic is placed in the `shared` Kotlin Multiplatform module. This includes:

- `model/`: data classes like `News`, `Media`, and `ApiResult`
- `data/`: separate packages for `remote` (Ktor) and `database` (SQLDelight)
- `repository/`: implements a clean data source strategy with remote-first, cache-fallback logic
- `ui/news/`: ViewModel (`NewsViewModel`) and the composable UI (`NewsListScreen`)
- `di/`: service locator (`AppModule.kt`) to provide dependencies across platforms
- `util/`: helper classes like `DatabaseMapper` and `DateUtils`

---

## Features

- Scrollable list of news stories with title, image, and date
- Pagination with a default (but configurable) page size
- Caching of fetched data for offline access
- Visual indication when loading or offline
- Dark/light theme support via custom `AppTheme`
- Includes **3 unit tests**:
    - Initial ViewModel state
    - Successful news fetch
    - Error scenario with server failure

---

## Getting Started

### Prerequisites

- Android Studio (Giraffe or newer)
- SQLDelight plugin installed
- Xcode (for iOS simulator and Compose Preview on iOS)

### How to Use the Component

1. Clone the repository and open the project in Android Studio.
2. Build the `:shared` module and sync dependencies.
3. To launch the component in Android, call:
   ```kotlin
   setContent { App() }

## Demo Videos

- [▶ Watch Android Demo](https://youtube.com/shorts/2VP2SwYMFT8)
- [▶ Watch iOS Demo](https://youtube.com/shorts/9otWs-hHTH8)
