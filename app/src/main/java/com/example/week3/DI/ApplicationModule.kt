package com.example.week3.DI

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides


@Module
class ApplicationModule (private val application: Application) {

    @Provides
    fun provideContext(): Context = application.applicationContext

    @Provides
    fun provideConnectivityManager(context: Context): ConnectivityManager =
        ContextCompat.getSystemService(context, ConnectivityManager::class.java) as ConnectivityManager
}