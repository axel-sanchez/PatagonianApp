package com.example.patagonianapp.domain.repository

import com.example.patagonianapp.helpers.Either
import com.example.patagonianapp.data.model.Result
import com.example.patagonianapp.data.room.LyricsDTO
import com.example.patagonianapp.data.source.ApiError

/**
 * @author Axel Sanchez
 */
interface LyricsRepository {
    suspend fun getLyricsByArtistAndTittle(artist: String, title: String): Either<ApiError, Result>
    suspend fun addLyrics(lyrics: LyricsDTO): Long
    suspend fun getAllLyrics(): List<LyricsDTO>
}