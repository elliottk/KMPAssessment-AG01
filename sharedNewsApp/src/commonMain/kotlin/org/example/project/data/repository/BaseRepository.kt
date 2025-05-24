package org.example.project.data.repository

import org.example.project.di.AppModule

open class BaseRepository {
    internal val remote by lazy { AppModule.remoteHelper }
    internal val database by lazy { AppModule.databaseHelper }
}