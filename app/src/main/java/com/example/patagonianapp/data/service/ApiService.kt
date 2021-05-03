package com.example.patagonianapp.data.service

import com.example.patagonianapp.data.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Axel Sanchez
 */
interface ApiService{
    @GET("{artist}/{title}")
    suspend fun searchLyricByArtistAndTittle(@Path("artist") artist: String, @Path("title") title: String): Response<Result?>
}