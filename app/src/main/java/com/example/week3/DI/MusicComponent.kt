package com.example.week3.DI

import com.example.week3.MainActivity
import com.example.week3.model.AllRockMusic
import com.example.week3.model.classic.AllClassicMusic
import com.example.week3.model.pop.AllPopMusic
import com.example.week3.ui.ClassicFragment
import com.example.week3.ui.PopFragment
import com.example.week3.ui.RockFragment
import dagger.Component


@Component(
    modules = [
        ApplicationModule::class,
        NetworkModule::class,
        PresenterModule::class,
        RepositoryModule::class
    ]
)


// Might need to change name of inject for pop, rock and classic
interface MusicComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(classicFragment: ClassicFragment)
    fun inject(popFragment: PopFragment)
    fun inject(rockFragment: RockFragment)

//    fun inject(allPopMusic: AllPopMusic)
//    fun inject(allRockMusic: AllRockMusic)
//    fun inject(allClassicMusic: AllClassicMusic)
}