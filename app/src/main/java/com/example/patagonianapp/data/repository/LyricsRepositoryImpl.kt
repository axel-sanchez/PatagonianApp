package com.example.patagonianapp.data.repository

import com.example.patagonianapp.helpers.Either
import com.example.patagonianapp.data.model.Result
import com.example.patagonianapp.data.source.ApiError
import com.example.patagonianapp.data.source.LyricRemoteSource
import com.example.patagonianapp.domain.repository.LyricsRepository

/**
 * @author Axel Sanchez
 */
class LyricsRepositoryImpl(private val lyricRemoteSource: LyricRemoteSource): LyricsRepository {
    override suspend fun getLyricByArtistAndTittle(artist: String, title: String): Either<ApiError, Result> {
        return lyricRemoteSource.searchLyricByArtistAndTittle(artist, title).value ?: Either.Left(ApiError.NO_LYRICS_FOUND)
    }
}