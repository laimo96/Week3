package com.example.week3.network

import com.example.week3.model.AllRockMusic
import com.example.week3.model.classic.AllClassicMusic
import com.example.week3.model.pop.AllPopMusic
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicService {

    @GET(ROCK_PATH)
    fun getRockMusic(): Single<List<AllRockMusic>>

    @GET(CLASSIC_PATH)
    fun getClassicMusic(): Single<List<AllClassicMusic>>

    @GET(POP_PATH)
    fun getPopMusic(): Single<List<AllPopMusic>>

    companion object{
        const val BASE_URL = "https://itunes.apple.com/"
        private const val CLASSIC_PATH = BASE_URL + "search?term=classick&amp;media=music&amp;entity=song&amp;limit=50"
        private const val POP_PATH = BASE_URL + "search?term=pop&amp;media=music&amp;entity=song&amp;limit=50"
        private const val ROCK_PATH = BASE_URL + "search?term=rock&amp;media=music&amp;entity=song&amp;limit=50"
    }
}