package com.example.infospot.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infospot.Adapters.CategoryAdapter
import com.example.infospot.Adapters.TopNewsAdapter
import com.example.infospot.R
import com.example.infospot.UI.ArticleWebViewActivity
import com.example.infospot.UI.CategoryActivity
import com.example.infospot.UI.NewsActivity
import com.example.infospot.UI.NewsViewModel
import com.example.infospot.utils.Constants.Companion.CATEGORY_LIST
import com.example.infospot.utils.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: TopNewsAdapter
    lateinit var categoryAdapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()


        newsAdapter.setOnItemClickListener {
            val intent = Intent(context, ArticleWebViewActivity::class.java)
            intent.putExtra("articleURL", it.url)
            startActivity(intent)
        }

        dateText.text = getDate()

        categoryAdapter.setOnItemClickListener { category: String, color: String ->
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("category", category)
            intent.putExtra("color", color)
            startActivity(intent)
        }

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

    private fun setupRecyclerView() {
        newsAdapter = TopNewsAdapter(viewModel)
        categoryAdapter = CategoryAdapter(CATEGORY_LIST)

        val linearLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val linearLayoutManager2 = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        topNewsRecyclerView.layoutManager = linearLayoutManager
        topNewsRecyclerView.adapter = newsAdapter

        categoryRecyclerView.layoutManager = linearLayoutManager2
        categoryRecyclerView.adapter = categoryAdapter
    }

    private fun hideProgressBar() {
        shimmer.stopShimmer()
        cardForShimmer.visibility = View.GONE
    }

    private fun showProgressBar() {
        shimmer.startShimmer()
    }

    private fun getDate(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = current.format(formatter)
        return formatted.substring(0, 11)
    }

}
