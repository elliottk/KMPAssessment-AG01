# NewsApp – Compose Multiplatform News Component

## Overview

**NewsApp** is a reusable, cross-platform UI component built with **Jetpack Compose Multiplatform (CMP)**. It is designed to be integrated into Android and iOS apps to display a paginated list of news stories. Each item includes a headline, image, and publish date. This is not a standalone application but a self-contained module that can be embedded into larger projects.

The component supports pagination, offline access via local caching, configurable page size, and dark mode (on Android). My focus was on building a clean, testable, and scalable module that demonstrates modern mobile architecture and Kotlin Multiplatform capabilities.

---

## Architectural Choices

### Model-View-Intent (MVI)

I selected the **MVI (Model-View-Intent)** architecture pattern because of its ability to enforce a **unidirectional data flow** and make state management more predictable. This pattern provides strong separation of concerns and makes the UI logic easier to test and maintain.

My MVI implementation includes:
- **Intent** – user actions like loading data
- **State** – the current UI data and state
- **Reducer** – updates state based on intents
- **ViewModel** – handles the logic, fetches data, and emits UI state

MVI is particularly well-suited for handling pagination, error messaging, and dynamic UI updates in Compose.

---

## Technology Stack

To support cross-platform development and clean architecture, I used:

- **Jetpack Compose Multiplatform** – for shared declarative UI on Android and iOS
- **Ktor Client** – for fetching data from the endpoint:  
  `https://cbcmusic.github.io/assessment-tmp/data/data.json`
- **Kotlinx Serialization** – for efficient and type-safe JSON parsing
- **SQLDelight** – for caching and offline support with compile-time SQL validation
- **Kotlin Coroutines** – for handling asynchronous operations like network calls and local DB interactions

---

## Shared Module Structure

All business logic is placed in the `:shared` Kotlin Multiplatform module. This includes:

- A data layer with remote and local sources
- A repository class to manage fetching and caching
- MVI components: intent classes, UI state, reducer, and shared ViewModel
- Logic for pagination, error handling, and caching

Platform-specific apps (e.g., `:androidApp`) use this shared logic to render the UI and apply native theming or platform-specific services.

---

## Features

- List of news stories with headline, image, and publish date
- Pagination with a configurable page size
- Caching of previously loaded data for offline support
- Night mode (on Android) using Compose theming
- **3 unit tests** included:
  - Initial ViewModel state validation
  - Successful news loading
  - Error handling with API failure

---

## Getting Started

### Prerequisites

- **Android Studio** (latest stable version)
- **SQLDelight plugin** (`Preferences > Plugins`)
- **Xcode** (optional, for iOS builds)

### How to Use

1. Clone the repository and open it in Android Studio.
2. Sync the Gradle project.
3. Review the `:shared` module for MVI and data logic.
4. Use the `NewsListView` composable in your Android or iOS Compose screen.
5. Set the page size or trigger pagination via the ViewModel.
6. Run unit tests under `shared/src/commonTest` to verify logic.

---

## Final Notes

This component was built with clarity, modularity, and multiplatform compatibility in mind. It demonstrates how MVI, Kotlin Multiplatform, and Compose can work together in a modern development workflow.

If you're new to the codebase, I recommend exploring the repository and ViewModel classes in the `shared` module first—they represent the core business logic and UI flow.

For questions about integrating or extending the component, please feel free to reach out via email or submit an issue in the repository. I'm happy to support or collaborate further.
