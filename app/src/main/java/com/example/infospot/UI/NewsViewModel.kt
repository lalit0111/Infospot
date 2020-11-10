package com.example.infospot.UI

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infospot.Repository.NewsRepository
import com.example.infospot.models.Article
import com.example.infospot.models.NewsResponse
import com.example.infospot.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNewsPage = 1

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNewsPage = 1

    init {
        getTopNews("in")
    }

    fun getTopNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getTopNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.insert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteSavedNews(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    suspend fun isArticleSaved(article: Article): Boolean {
        var a: Article? = null
        Log.d("IsArticleSaved", "job started")
        val job = viewModelScope.launch(Dispatchers.IO) {
            a = newsRepository.isArticleSaved(article)
            Log.d("IsArticleSaved", a.toString())
        }
        job.join()
        Log.d("IsArticleSaved", "job finish")
        return a != null
    }


}