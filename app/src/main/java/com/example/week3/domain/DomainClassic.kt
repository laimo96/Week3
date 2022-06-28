package com.example.week3.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.week3.model.classic.AllClassicMusic

@Entity(tableName = "music_table_classic")
data class DomainClassic(

    @PrimaryKey
    val artistId: Int,
    val trackId: Int,
    val artistName: String,
    val collectionName: String,
    val artworkUrl60: String,
    val trackPrice: Double
)

fun List<AllClassicMusic>.mapToDomainClassicSong(): List<DomainClassic>{
    return this.map { networkClassicSong ->

        DomainClassic(
            artistId = networkClassicSong.artistId ?: 0,
            trackId = networkClassicSong.trackId ?: 0,
            artistName = networkClassicSong.artistName ?: "Name Not Found",
            collectionName = networkClassicSong.collectionName ?: "Collection Not Found",
            artworkUrl60 = networkClassicSong.artworkUrl60 ?: "Logo Not Found",
            trackPrice = networkClassicSong.trackPrice ?: 0.0
        )
    }
}