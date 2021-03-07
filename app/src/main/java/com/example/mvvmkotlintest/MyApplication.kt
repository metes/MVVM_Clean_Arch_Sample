package com.example.mvvmkotlintest

import android.app.Application

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        // initStetho()
    }

    /*
    fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
    */
}
