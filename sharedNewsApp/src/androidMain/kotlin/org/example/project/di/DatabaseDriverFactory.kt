package org.example.project.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.example.project.database.NewsDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(context: Any?): SqlDriver {
        return AndroidSqliteDriver(schema = NewsDatabase.Schema, context as Context, "news.db")
    }
}