package com.example.week3.DI

import com.example.week3.network.MusicRepository
import com.example.week3.network.MusicRepositoryImpl
import com.example.week3.presenter.*
import dagger.Binds
import dagger.Module


@Module
abstract class RepositoryModule {

    // @Binds  works faster than @Provides with abstract classes and interfaces
    @Binds
    abstract fun provideMusicRepository(musicRepositoryImpl: MusicRepositoryImpl): MusicRepository
}


@Module
abstract class PresenterModule {

    @Binds
    abstract fun provideClassicPresenter(classicMusicPresenter: ClassicMusicPresenter): ClassicPresenterContract

    @Binds
    abstract fun providePopPresenter(popMusicPresenter: PopMusicPresenter): PopPresenterContract

    @Binds
    abstract fun provideRockPresenter(rockMusicPresenter: RockMusicPresenter): RockPresenterContract
}