package com.example.patagonianapp.domain.usecase

import com.example.patagonianapp.data.room.LyricsDTO
import com.example.patagonianapp.domain.repository.LyricsRepository

/**
 * @author Axel Sanchez
 */

interface GetHistoryUseCase{
    suspend fun call(): List<LyricsDTO>
}

class GetHistoryUseCaseImpl(private val repository: LyricsRepository): GetHistoryUseCase {
    override suspend fun call(): List<LyricsDTO> = repository.getAllLyrics()
}