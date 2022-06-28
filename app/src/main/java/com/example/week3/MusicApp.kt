package com.example.week3

import android.app.Application
import com.example.week3.DI.ApplicationModule
import com.example.week3.DI.DaggerMusicComponent
import com.example.week3.DI.MusicComponent

class MusicApp : Application() {

    override fun onCreate() {
        super.onCreate()

        musicComponent = DaggerMusicComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

    }

    companion object{
        lateinit var musicComponent: MusicComponent
    }
}