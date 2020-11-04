package com.example.infospot.Repository

import com.example.infospot.API.RetrofitInstance
import com.example.infospot.DB.ArticleDatabase

class NewsRepository(
    val articleDatabase: ArticleDatabase
) {
    suspend fun getTopNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getTopNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchNews(searchQuery, pageNumber)
}