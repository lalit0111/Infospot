package com.example.infospot.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infospot.Adapters.SavedAndSearchNewsAdapter
import com.example.infospot.R
import com.example.infospot.UI.NewsActivity
import com.example.infospot.UI.NewsViewModel
import kotlinx.android.synthetic.main.fragment_saved_news.*

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var savedAndSearchNewsAdapter: SavedAndSearchNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        savedAndSearchNewsAdapter = SavedAndSearchNewsAdapter()

        val linearLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        savedNewsRecyclerView.layoutManager = linearLayoutManager
        savedNewsRecyclerView.adapter = savedAndSearchNewsAdapter

    }

}