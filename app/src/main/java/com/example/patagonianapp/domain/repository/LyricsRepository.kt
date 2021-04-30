package com.example.patagonianapp.domain.repository

import com.example.patagonianapp.helpers.Either
import com.example.patagonianapp.data.model.Result
import com.example.patagonianapp.data.source.ApiError

/**
 * @author Axel Sanchez
 */
interface LyricsRepository {
    suspend fun getLyricByArtistAndTittle(artist: String, title: String): Either<ApiError, Result>
}