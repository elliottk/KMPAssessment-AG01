package org.example.project.data.database

import org.example.project.di.AppModule

open class BaseDatabaseHelper {
    internal val database by lazy { AppModule.database }
}