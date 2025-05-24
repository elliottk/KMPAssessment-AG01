package org.example.project.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.example.project.database.NewsDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(context: Any?): SqlDriver {
        return NativeSqliteDriver(NewsDatabase.Schema, "news.db")
    }
}