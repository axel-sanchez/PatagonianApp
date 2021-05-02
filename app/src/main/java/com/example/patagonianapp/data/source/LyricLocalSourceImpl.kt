package com.example.patagonianapp.data.source

import com.example.patagonianapp.data.room.LyricsDTO
import com.example.patagonianapp.data.room.LyricsDao

/**
 * @author Axel Sanchez
 */
interface LyricLocalSource{
    suspend fun getAllLyrics(): List<LyricsDTO>
    suspend fun addLyrics(lyrics: LyricsDTO): Long
    suspend fun searchLyricByArtistAndTittle(artist: String, title: String): LyricsDTO?
}

class LyricLocalSourceImpl(private val database: LyricsDao): LyricLocalSource {
    override suspend fun getAllLyrics(): List<LyricsDTO> = database.getAllLyrics()

    override suspend fun addLyrics(lyrics: LyricsDTO): Long = database.insertLyrics(lyrics)

    override suspend fun searchLyricByArtistAndTittle(artist: String, title: String): LyricsDTO? = database.getLyrics(artist, title)
}