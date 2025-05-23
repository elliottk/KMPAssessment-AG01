package org.example.project.data.repository

import org.example.project.di.AppModule

open class BaseApiRepository {
    internal val remote by lazy { AppModule.remoteHelper }
}