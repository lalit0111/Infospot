package com.example.infospot.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infospot.Adapters.SavedAndSearchNewsAdapter
import com.example.infospot.R
import com.example.infospot.UI.ArticleWebViewActivity
import com.example.infospot.UI.NewsActivity
import com.example.infospot.UI.NewsViewModel
import com.example.infospot.utils.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.example.infospot.utils.Resource
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var searchAndSearchNewsAdapter: SavedAndSearchNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        searchAndSearchNewsAdapter.setOnItemClickListener {
            val intent = Intent(context, ArticleWebViewActivity::class.java)
            intent.putExtra("articleURL", it.url)
            startActivity(intent)
        }

        var job: Job? = null
        search_bar.setOnSearchTextChangeListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }

        search_bar.setOnSearchListener { editable ->
            editable.let {
                if (editable.isNotEmpty()) {
                    viewModel.searchNews(editable)
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { newsResponse ->
                        searchAndSearchNewsAdapter.differ.submitList(newsResponse.articles)
                        disableSearchIllustration()
                    }
                }

                is Resource.Error -> {
                    response.message?.let { message ->
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        disableSearchIllustration()
                    }
                }

                is Resource.Loading -> {
                    enableSearchIllustration()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        searchAndSearchNewsAdapter = SavedAndSearchNewsAdapter()

        val linearLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        searchNewsRecyclerView.layoutManager = linearLayoutManager
        searchNewsRecyclerView.adapter = searchAndSearchNewsAdapter
    }

    private fun enableSearchIllustration() {
        searchIllustration.visibility = View.VISIBLE
    }

    private fun disableSearchIllustration() {
        searchIllustration.visibility = View.GONE
    }
}