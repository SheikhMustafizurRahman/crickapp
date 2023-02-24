package com.example.cricketoons.util

import android.app.Application

class MyContext : Application() {

    companion object {
        lateinit var instanceContext: MyContext
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instanceContext = this
    }
}
