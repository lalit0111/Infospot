package com.example.infospot.Repository

import com.example.infospot.API.RetrofitInstance
import com.example.infospot.DB.ArticleDatabase
import com.example.infospot.models.Article

class NewsRepository(
    val articleDatabase: ArticleDatabase
) {
    suspend fun getTopNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getTopNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchNews(searchQuery, pageNumber)

    suspend fun insert(article: Article) = articleDatabase.getArticleDao().instert(article)

    fun getSavedNews() = articleDatabase.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) =
        articleDatabase.getArticleDao().deleteArticle(article)

    suspend fun isArticleSaved(article: Article) =
        articleDatabase.getArticleDao().getArticleByTitle(article.title)
}