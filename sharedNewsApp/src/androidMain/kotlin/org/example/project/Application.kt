package org.example.project

import android.app.Application
import org.example.project.di.AppModule

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        AppModule.applicationContext = applicationContext
    }
}