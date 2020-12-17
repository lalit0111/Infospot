package com.example.infospot.DB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.infospot.models.Article

@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun instert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Query("SELECT * FROM articles WHERE title= :q")
    suspend fun getArticleByTitle(q: String): Article

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("DELETE FROM articles")
    suspend fun deleteAllArticle()
}