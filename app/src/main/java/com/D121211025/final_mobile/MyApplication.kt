package com.D121211025.final_mobile

import android.app.Application
import com.D121211025.final_mobile.data.AppContainer
import com.D121211025.final_mobile.data.DefaultAppContainer

class MyApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}