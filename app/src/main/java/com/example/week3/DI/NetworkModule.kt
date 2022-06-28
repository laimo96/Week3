package com.example.week3.DI

import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.week3.network.MusicService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


@Module
class NetworkModule {

    @Provides // here providing list type of objects
    fun provideMoshi() : Moshi =
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun provideOkHttpClient(loggingInterceptor : HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()


    @Provides
    fun providesMusicService(okHttpClient: OkHttpClient, moshi: Moshi) : MusicService =
        Retrofit.Builder()
            .baseUrl(MusicService.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))  //it will convert, serialize json objects
            .client(okHttpClient)
            .build()
            .create(MusicService::class.java)

    @Provides
    fun provideNetworkRequest(): NetworkRequest=
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
}