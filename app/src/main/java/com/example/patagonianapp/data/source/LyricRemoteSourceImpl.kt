package com.example.patagonianapp.data.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.patagonianapp.helpers.Either
import com.example.patagonianapp.data.model.Result
import com.example.patagonianapp.data.service.ApiService

/**
 * @author Axel Sanchez
 */

interface LyricRemoteSource{
    suspend fun searchLyricByArtistAndTittle(artist: String, title: String): MutableLiveData<Either<ApiError, Result>>
}

class LyricRemoteSourceImpl(private val service: ApiService): LyricRemoteSource {
    override suspend fun searchLyricByArtistAndTittle(artist: String, title: String): MutableLiveData<Either<ApiError, Result>> {
        val mutableLiveData = MutableLiveData<Either<ApiError, Result>>()

        try {
            val response = service.searchLyricByArtistAndTittle(artist, title)
            if (response.isSuccessful) {
                if(response.code() == 200){
                    Log.i("Successful Response", response.body().toString())

                    response.body()?.let { lyric ->
                        mutableLiveData.value = Either.Right(lyric)
                    }?: kotlin.run {
                        mutableLiveData.value = Either.Left(ApiError.NO_LYRICS_FOUND)
                    }

                } else if(response.code() == 404){
                    Log.i("CODE 404", "No lyrics found")
                    mutableLiveData.value = Either.Left(ApiError.NO_LYRICS_FOUND)
                }
            } else {
                Log.i("Error Response", response.errorBody().toString())
                mutableLiveData.value = Either.Left(ApiError.NO_LYRICS_FOUND)
            }
        } catch (e: Exception) {
            mutableLiveData.value = Either.Left(ApiError.NO_LYRICS_FOUND)
            Log.e("LyricRemoteSourceImpl", "Error al obtener las playlists y guardarlos en el livedata")
            e.printStackTrace()
        }

        return mutableLiveData
    }
}

enum class ApiError(val error: String){
    NO_LYRICS_FOUND("No lyrics found")
}