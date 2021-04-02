package dev.eduayuso.cgkotlin

import android.app.Application

open class CGApp: Application() {

    override fun onCreate() {

        super.onCreate()

        Thread.setDefaultUncaughtExceptionHandler { _: Thread?, ex: Throwable ->
            ex.printStackTrace()
            System.exit(0)
        }
    }
}