# NewsApp – Compose Multiplatform News Component

## Overview

This project is a reusable **Jetpack Compose Multiplatform (CMP)** component that displays a list of formatted news stories. It is designed to be embedded in both Android and iOS applications, and **is not a standalone app**. Each story item includes a headline, image, and published date, and the list supports pagination, offline access, and dynamic page size configuration.

---

## Architectural Choices

### Architecture Pattern: MVI (Model-View-Intent)

I selected the **MVI architecture pattern** to enforce a **unidirectional data flow** and maintain predictable state management. This approach allowed me to clearly separate concerns between UI, logic, and data. It simplified testing, reduced bugs, and worked naturally with Jetpack Compose's reactive design.

The MVI layer includes:
- **Intent** classes for user actions
- **State** data classes representing UI state
- **Reducers** to manage transitions
- **ViewModel** for orchestrating logic and exposing state

---

## Technologies & Libraries

I made deliberate choices to keep the codebase modern, clean, and compatible with Kotlin Multiplatform best practices:

- **Jetpack Compose Multiplatform** – Enables shared UI code across Android and iOS.
- **Ktor Client** – A Kotlin-first asynchronous networking library to fetch news stories from:  
  `https://cbcmusic.github.io/assessment-tmp/data/data.json`
- **Kotlinx Serialization** – For type-safe and efficient JSON parsing.
- **SQLDelight** – Used for local caching and offline access. It provides a type-safe interface and compile-time SQL checks.
- **Kotlin Coroutines** – Used throughout for managing asynchronous tasks like network and database operations.

---

## Shared Module (`:shared`)

All core logic lives in the `shared` module, which includes:
- **Data Layer**: API service, local database access, and repository.
- **Business Logic**: Pagination logic, caching strategies, and offline handling.
- **MVI Components**: Intent, State, Reducers, and shared ViewModel.

This separation ensures that the Android and iOS apps can use the same logic without duplication.

---

## Features

- List of news stories with headline, image, and publish date
- **Pagination** with configurable page size
- **Offline support** using SQLDelight cache
- **Night mode** support on Android via Compose theming
- Minimum **3 unit tests** included for core logic

---

## How to Use the Component

### Prerequisites

- **Android Studio (Latest Stable)**
- **SQLDelight Plugin**: Install via `Settings > Plugins`
- **Xcode (Optional)** for iOS integration

### Integration Steps

1. Clone the repository and open in Android Studio.
2. Build and sync the project.
3. Locate the `shared` module.
4. Use the provided `NewsListView` composable inside your Compose screen.
5. Set page size and any required configurations via ViewModel or repository.
6. Run included unit tests (`shared/src/commonTest`) to verify logic.

---

## Final Notes

This component is designed to be plug-and-play across platforms. If you are extending the project, you may consider abstracting themes further or enhancing the pagination model with remote keys.

For any questions about architecture or integration, feel free to explore the shared module or reach out.

