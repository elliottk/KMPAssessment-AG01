# NewsApp – Compose Multiplatform News Component

## Overview

**NewsApp** is a reusable user interface component built using **Jetpack Compose Multiplatform (CMP)**. It displays a scrollable, formatted list of news stories and is designed to be embedded in both Android and iOS mobile applications. This is not a standalone app, but a cross-platform module that can be integrated into larger projects.

Each list item includes a headline, image, and published date. The component supports pagination with configurable page size, offline access using local caching, and night mode support on Android.

This document serves as a guide for onboarding new developers to the project by explaining the architecture, technology choices, and steps for integration.

---

## Architectural Choices

### Model-View-Intent (MVI)

I chose the **MVI (Model-View-Intent)** architectural pattern to create a predictable, testable, and unidirectional flow of data. MVI ensures that every UI state is a function of a known model and intent, which simplifies debugging and aligns naturally with the reactive UI paradigm of Jetpack Compose.

The MVI structure includes:
- **Intent**: Represents user actions (e.g., load more, refresh)
- **State**: Represents the UI state (loading, success, error)
- **Reducer**: Defines how state changes in response to intents
- **ViewModel**: Coordinates logic, processes intents, and exposes state to the UI

---

## Technology Stack

To ensure reliability, maintainability, and multiplatform support, I chose the following tools:

- **Jetpack Compose Multiplatform**: Shared declarative UI for Android and iOS.
- **Ktor Client**: To fetch data from the endpoint  
  `https://cbcmusic.github.io/assessment-tmp/data/data.json`.
- **Kotlinx Serialization**: Type-safe JSON parsing.
- **SQLDelight**: Local database solution with Kotlin-first support and type-safe SQL.
- **Kotlin Coroutines**: For non-blocking, asynchronous operations throughout the app.

---

## Shared Module

All application logic is written in the `:shared` Kotlin Multiplatform module. This includes:

- Remote and local data sources
- Repository to handle caching and data flow
- MVI components (intent, state, reducer)
- ViewModel exposing a unified interface for both platforms

This design ensures platform-agnostic business logic that can be reused on Android and iOS.

---

## Features

- Displays a list of news stories with a headline, image, and date
- Supports pagination with a configurable page size
- Offline caching of previously loaded stories
- Night mode (on Android) using Jetpack Compose themes
- Minimum of **3 unit tests** covering:
    - JSON parsing
    - Pagination logic
    - Offline data handling

---

## Getting Started

### Prerequisites

- Android Studio (latest stable version)
- SQLDelight plugin (install via `Settings > Plugins`)
- Xcode (optional, for iOS testing)

### Setup Instructions

1. Clone the repository and open it in Android Studio.
2. Sync Gradle and build the project.
3. Explore the `:shared` module to understand the architecture.
4. Embed the `NewsListView` composable in your Compose screen (Android/iOS).
5. Adjust `pageSize` and data loading logic through the ViewModel.
6. Run tests in `shared/src/commonTest` to verify core functionality.

---

## Final Notes

This component was developed with clarity, scalability, and cross-platform compatibility in mind. The shared module encapsulates the core logic, making it easy to extend, test, or integrate into new applications.

If you're contributing or customizing the component, I recommend starting with the repository and ViewModel structure. For error handling, caching, and pagination behavior, those layers define most of the logic.

For questions about integrating or extending the component, please feel free to reach out via email or submit an issue in the repository. I'm happy to assist or provide further clarification if needed.
