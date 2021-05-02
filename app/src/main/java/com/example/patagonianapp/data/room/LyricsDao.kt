package com.example.patagonianapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * @author Axel Sanchez
 */
@Dao
interface LyricsDao {
    @Query("SELECT * FROM Lyrics order by id desc")
    suspend fun getAllLyrics(): List<LyricsDTO>

    @Query("SELECT * FROM Lyrics where artist like :artist and title like :title")
    suspend fun getLyrics(artist: String, title: String): LyricsDTO?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLyrics(lyrics: LyricsDTO): Long
}