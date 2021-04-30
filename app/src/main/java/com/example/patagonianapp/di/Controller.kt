package com.example.patagonianapp.di

import com.example.patagonianapp.data.repository.LyricsRepositoryImpl
import com.example.patagonianapp.data.service.ApiService
import com.example.patagonianapp.data.source.LyricRemoteSource
import com.example.patagonianapp.data.source.LyricRemoteSourceImpl
import com.example.patagonianapp.domain.repository.LyricsRepository
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

    single<LyricsRepository> { LyricsRepositoryImpl(get() as LyricRemoteSource) }
    single<LyricRemoteSource> { LyricRemoteSourceImpl(get() as ApiService) }
    single<GetTheLyricsOfTheSongUseCase> { GetTheLyricsOfTheSongUseCaseImpl(get() as LyricsRepository) }
}