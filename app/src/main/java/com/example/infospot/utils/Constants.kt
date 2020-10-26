package com.example.infospot.utils

import com.example.infospot.R
import com.example.infospot.models.Category

class Constants {
    companion object {
        const val API_KEY = "ee36c9b0e6fc4779aba78a2e217a1b28"
        const val BASE_URL = "https://newsapi.org"
        val CATEGORY_LIST = arrayListOf<Category>(
            Category("business", R.drawable.business),
            Category("entertainment", R.drawable.flat),
            Category("general", R.drawable.general),
            Category("health", R.drawable.health),
            Category("science", R.drawable.science),
            Category("sports", R.drawable.sports),
            Category("technology", R.drawable.technology)
        )
    }
}