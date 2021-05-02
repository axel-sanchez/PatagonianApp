package com.example.patagonianapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Axel Sanchez
 */
@Entity(tableName = "Lyrics") data class LyricsDTO(
    @PrimaryKey(autoGenerate = true) var id: Long,
    val artist: String,
    val title: String,
    val lyrics: String)