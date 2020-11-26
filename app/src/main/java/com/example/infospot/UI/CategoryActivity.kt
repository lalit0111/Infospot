package com.example.infospot.UI

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infospot.Adapters.SavedAndSearchNewsAdapter
import com.example.infospot.DB.ArticleDatabase
import com.example.infospot.R
import com.example.infospot.Repository.NewsRepository
import com.example.infospot.utils.Resource
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    lateinit var searchAndSearchNewsAdapter: SavedAndSearchNewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val category = intent.getStringExtra("category")
        val color = intent.getStringExtra("color")

        val NewsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(NewsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        if (category != null) {
            viewModel.getCategoryNews("in", category)
        }

        showCategoryTextView.text = category
        setupToolbar(color)
        setupRecyclerView()

        viewModel.categoryNews.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideShimmer()
                    response.data?.let { newsResponse ->
                        searchAndSearchNewsAdapter.differ.submitList(newsResponse.articles)
                    }
                }

                is Resource.Error -> {
                    hideShimmer()
                    response.message?.let { message ->
                        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                    }
                }

                is Resource.Loading -> {
                    showShimmer()
                }
            }
        })

        closeButton.setOnClickListener {
            onBackPressed()
            window.statusBarColor = Color.WHITE
        }
    }

    private fun setupRecyclerView() {
        searchAndSearchNewsAdapter = SavedAndSearchNewsAdapter()

        val linearLayoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.VERTICAL,
            false
        )
        showCategoryRecyclerView.layoutManager = linearLayoutManager
        showCategoryRecyclerView.adapter = searchAndSearchNewsAdapter
    }

    private fun setupToolbar(color: String?) {
        showCategoryToolbar.setCardBackgroundColor(Color.parseColor(color))
        window.statusBarColor = Color.parseColor(color)
    }

    private fun hideShimmer() {
        categoryShimmer.stopShimmer()
        shimmmerCardLayout.visibility = View.GONE
    }

    private fun showShimmer() {
        categoryShimmer.startShimmer()
    }
}