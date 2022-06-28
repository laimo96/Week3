package com.example.week3.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.week3.model.AllRockMusic


@Entity(tableName = "music_table_rock")
data class DomainRock(

    @PrimaryKey
    val artistId: Int,
    val trackId: Int,
    val artistName: String,
    val collectionName: String,
    val artworkUrl60: String,
    val trackPrice: Double
)

fun List<AllRockMusic>.mapToDomainRockSong(): List<DomainRock>{
    return this.map { networkRockSong ->

        DomainRock(
            artistId = networkRockSong.artistId ?: 0,
            trackId = networkRockSong.trackId ?: 0,
            artistName = networkRockSong.artistName ?: "Name Not Found",
            collectionName = networkRockSong.collectionName ?: "Collection Not Found",
            artworkUrl60 = networkRockSong.artworkUrl60 ?: "Logo Not Found",
            trackPrice = networkRockSong.trackPrice ?: 0.0
        )
    }
}
