package com.draccoapp.myapplication.api.services.comic

import com.draccoapp.myapplication.api.models.ComicResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.security.PublicKey

interface ComicService {

    @GET("public/comics")
    suspend fun getComicList(
        @Query("ts") timeStamp: String,
        @Query("apikey") publicKey: String,
        @Query("hash") hash: String,
        @Query("format") format: String?,
        @Query("formatType") formatType: String?,
        @Query("noVariants") noVariants: Boolean?,
        @Query("hasDigitalIssue") hasDigitalIssue: Boolean?,
        @Query("limit") limit: Int
    ): Response<ComicResponse>
}