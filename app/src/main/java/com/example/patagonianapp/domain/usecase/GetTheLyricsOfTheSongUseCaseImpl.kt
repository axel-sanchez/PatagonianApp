package com.example.patagonianapp.domain.usecase

import com.example.patagonianapp.helpers.Either
import com.example.patagonianapp.data.model.Result
import com.example.patagonianapp.data.source.ApiError
import com.example.patagonianapp.domain.repository.LyricsRepository

/**
 * @author Axel Sanchez
 */

interface GetTheLyricsOfTheSongUseCase {
    suspend fun call(artist: String, title: String): Either<ApiError, Result>
}

class GetTheLyricsOfTheSongUseCaseImpl(private val repository: LyricsRepository): GetTheLyricsOfTheSongUseCase {
    override suspend fun call(artist: String, title: String): Either<ApiError, Result> {
        return repository.getLyricByArtistAndTittle(artist, title)
    }
}