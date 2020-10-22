package com.example.infospot.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.infospot.R
import com.example.infospot.UI.NewsActivity
import com.example.infospot.UI.NewsViewModel

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = (activity as NewsActivity).viewModel

    }
}