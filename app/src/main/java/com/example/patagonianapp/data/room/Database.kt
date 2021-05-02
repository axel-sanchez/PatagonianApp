package com.example.patagonianapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Base de datos utilizando room
 * @author Axel Sanchez
 */
@Database(
    entities = [LyricsDTO::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase() {
    abstract fun lyricsDao(): LyricsDao
}