package com.example.week3.model.pop


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopMusic(
    @Json(name = "resultCount")
    val resultCount: Int?,
    @Json(name = "results")
    val allPopMusics: List<AllPopMusic>?
)