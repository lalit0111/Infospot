package com.example.infospot.utils

import com.example.infospot.R
import com.example.infospot.models.Category

class Constants {
    companion object {
        const val API_KEY = "ee36c9b0e6fc4779aba78a2e217a1b28"
        const val BASE_URL = "https://newsapi.org"
        const val SPLASH_TIME_OUT: Long = 5000L
        const val SEARCH_NEWS_TIME_DELAY = 500L
        val CATEGORY_LIST = arrayListOf(
            Category("business", "#CCFFFC", R.drawable.business),
            Category("entertainment", "#FFF7CF", R.drawable.flat),
            Category("general", "#ebf2fa", R.drawable.general),
            Category("health", "#ffe5d9", R.drawable.health),
            Category("science", "#FFF7CF", R.drawable.science),
            Category("sports", "#D6FFCC", R.drawable.sports),
            Category("technology", "#CCFFFC", R.drawable.technology)
        )
    }
}