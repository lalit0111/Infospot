package com.example.infospot.API

import com.example.infospot.models.NewsResponse
import com.example.infospot.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("/v2/top-headlines")
    suspend fun getTopNews(
        @Query("country")
        countryCode: String = "in",
        @Query("page")
        pageNo: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun searchNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNo: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}