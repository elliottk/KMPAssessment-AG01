package org.example.project.di

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory() {
    fun createDriver(context: Any?): SqlDriver
}