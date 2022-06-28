package com.example.week3.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RockMusic(
    @Json(name = "resultCount")
    val resultCount: Int?,
    @Json(name = "results")
    val allRockMusics: List<AllRockMusic>?
)