package com.example.week3.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.week3.model.pop.AllPopMusic

@Entity(tableName = "music_table_pop")
data class DomainPop(

    @PrimaryKey
    val artistId: Int,
    val trackId: Int,
    val artistName: String,
    val collectionName: String,
    val artworkUrl60: String,
    val trackPrice: Double
)

fun List<AllPopMusic>.mapToDomainPopSong(): List<DomainPop>{
    return this.map { networkPopSong ->

        DomainPop(
            artistId = networkPopSong.artistId ?: 0,
            trackId = networkPopSong.trackId ?: 0,
            artistName = networkPopSong.artistName ?: "Name Not Found",
            collectionName = networkPopSong.collectionName ?: "Collection Not Found",
            artworkUrl60 = networkPopSong.artworkUrl60 ?: "Logo Not Found",
            trackPrice = networkPopSong.trackPrice ?: 0.0
        )
    }
}