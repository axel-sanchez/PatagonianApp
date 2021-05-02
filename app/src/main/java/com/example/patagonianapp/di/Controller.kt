package com.example.patagonianapp.di

import androidx.room.Room
import com.example.patagonianapp.data.repository.LyricsRepositoryImpl
import com.example.patagonianapp.data.room.Database
import com.example.patagonianapp.data.room.LyricsDao
import com.example.patagonianapp.data.service.ApiService
import com.example.patagonianapp.data.source.LyricLocalSource
import com.example.patagonianapp.data.source.LyricLocalSourceImpl
import com.example.patagonianapp.data.source.LyricRemoteSource
import com.example.patagonianapp.data.source.LyricRemoteSourceImpl
import com.example.patagonianapp.domain.repository.LyricsRepository
import com.example.patagonianapp.domain.usecase.GetHistoryUseCase
import com.example.patagonianapp.domain.usecase.GetHistoryUseCaseImpl
import com.example.patagonianapp.domain.usecase.GetTheLyricsOfTheSongUseCase
import com.example.patagonianapp.domain.usecase.GetTheLyricsOfTheSongUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.lyrics.ovh/v1/"

/**
 * @author Axel Sanchez
 */
val moduleApp = module {
    single{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single{ (get() as Retrofit).create(ApiService::class.java) }

    single<LyricsRepository> { LyricsRepositoryImpl(get() as LyricRemoteSource, get() as LyricLocalSource) }
    single<LyricRemoteSource> { LyricRemoteSourceImpl(get() as ApiService) }
    single<GetTheLyricsOfTheSongUseCase> { GetTheLyricsOfTheSongUseCaseImpl(get() as LyricsRepository) }

    single { Room
        .databaseBuilder(androidContext(), Database::class.java, "patagonianDB.db3")
        .build() }

    single<LyricLocalSource> { LyricLocalSourceImpl((get() as Database).lyricsDao()) }
    single<GetHistoryUseCase> { GetHistoryUseCaseImpl(get() as LyricsRepository) }
}