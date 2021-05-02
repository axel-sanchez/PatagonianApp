package com.example.patagonianapp.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson

/**
 * @author Axel Sanchez
 */
class Converters {

    private val gson: Gson = Gson()

    @TypeConverter
    fun fromLyrics(lyrics: LyricsDTO): String {
        return gson.toJson(lyrics)
    }

    @TypeConverter
    fun toLyrics(lyricsGson: String): LyricsDTO {
        return gson.fromJson(lyricsGson, LyricsDTO::class.java)
    }
}