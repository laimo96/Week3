package com.example.week3.model.classic


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClassicMusic(
    @Json(name = "resultCount")
    val resultCount: Int?,
    @Json(name = "results")
    val allClassicMusics: List<AllClassicMusic>?
)