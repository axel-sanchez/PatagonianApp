package com.example.patagonianapp.data.repository

import com.example.patagonianapp.helpers.Either
import com.example.patagonianapp.data.model.Result
import com.example.patagonianapp.data.room.LyricsDTO
import com.example.patagonianapp.data.source.ApiError
import com.example.patagonianapp.data.source.LyricLocalSource
import com.example.patagonianapp.data.source.LyricRemoteSource
import com.example.patagonianapp.domain.repository.LyricsRepository

/**
 * @author Axel Sanchez
 */
class LyricsRepositoryImpl(private val lyricRemoteSource: LyricRemoteSource, private val lyricLocalSource: LyricLocalSource): LyricsRepository {
    override suspend fun getLyricsByArtistAndTittle(artist: String, title: String): Either<ApiError, Result> {
        val localLyrics = lyricLocalSource.searchLyricByArtistAndTittle(artist, title)
        localLyrics?.let {
            return Either.Right(Result(localLyrics.lyrics))
        }?: kotlin.run {
            val result = lyricRemoteSource.searchLyricByArtistAndTittle(artist, title).value ?: Either.Left(ApiError.NO_LYRICS_FOUND)
            if(result is Either.Right){
                addLyrics(LyricsDTO(0, artist, title, result.r.lyrics))
            }
            return result
        }
    }

    override suspend fun addLyrics(lyrics: LyricsDTO): Long = lyricLocalSource.addLyrics(lyrics)

    override suspend fun getAllLyrics(): List<LyricsDTO> = lyricLocalSource.getAllLyrics()
}