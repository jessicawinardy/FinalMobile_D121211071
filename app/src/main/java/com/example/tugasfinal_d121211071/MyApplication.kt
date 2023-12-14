package com.example.tugasfinal_d121211071

import android.app.Application
import com.example.tugasfinal_d121211071.data.AppContainer
import com.example.tugasfinal_d121211071.data.DefaultAppContainer

class MyApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}