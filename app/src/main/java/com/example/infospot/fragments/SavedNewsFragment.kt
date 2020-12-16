package com.example.infospot.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infospot.Adapters.SavedAndSearchNewsAdapter
import com.example.infospot.R
import com.example.infospot.UI.NewsActivity
import com.example.infospot.UI.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlinx.android.synthetic.main.fragment_search_news.*

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var savedAndSearchNewsAdapter: SavedAndSearchNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        if (savedAndSearchNewsAdapter.differ.currentList.isNotEmpty()) {
            searchIllustration.visibility = View.GONE
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = savedAndSearchNewsAdapter.differ.currentList[position]
                viewModel.deleteSavedNews(article)
                Snackbar.make(
                    viewHolder.itemView,
                    "Sucessfully Deleted Article!!",
                    Snackbar.LENGTH_LONG
                ).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(savedNewsRecyclerView)
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner, { articles ->
            savedAndSearchNewsAdapter.differ.submitList(articles)
        })
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