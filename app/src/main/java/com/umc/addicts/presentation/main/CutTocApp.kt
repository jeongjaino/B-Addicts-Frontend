package com.umc.addicts.presentation.main

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CutTocApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}