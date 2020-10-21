package com.example.infospot.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infospot.Adapters.TopNewsAdapter
import com.example.infospot.R
import kotlinx.android.synthetic.main.fragment_breaking_news.*


class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //Create Adapter and LayoutManager
        val adapter = TopNewsAdapter()
        val linearLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // Set up recyclerView
        topNewsRecyclerView.layoutManager = linearLayoutManager
        topNewsRecyclerView.adapter = adapter

        setGradientBackgroundsTocards()

        super.onViewCreated(view, savedInstanceState)
    }

    fun setGradientBackgroundsTocards() {

        recentNewsCategoryCard1.background = resources.getDrawable(R.drawable.gradient1)
        recentNewsCategoryCard2.background = resources.getDrawable(R.drawable.gradient2)
    }
}