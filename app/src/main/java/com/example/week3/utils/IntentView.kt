package com.example.week3.utils

sealed class IntentView {
    object AllRockMusic : IntentView()
    object AllPopMusic : IntentView()
    object AllClassicMusic : IntentView()
}
