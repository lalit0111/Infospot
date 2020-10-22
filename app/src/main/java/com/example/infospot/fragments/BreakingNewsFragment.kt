package com.example.infospot.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infospot.Adapters.TopNewsAdapter
import com.example.infospot.R
import com.example.infospot.UI.NewsActivity
import com.example.infospot.UI.NewsViewModel
import com.example.infospot.utils.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*


class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: TopNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()
        setGradientBackgroundsTocards()

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

    }

    private fun setGradientBackgroundsTocards() {
        recentNewsCategoryCard1.background = resources.getDrawable(R.drawable.gradient1)
        recentNewsCategoryCard2.background = resources.getDrawable(R.drawable.gradient2)
    }

    private fun setupRecyclerView() {
        newsAdapter = TopNewsAdapter()
        val linearLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        topNewsRecyclerView.layoutManager = linearLayoutManager
        topNewsRecyclerView.adapter = newsAdapter
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }
}