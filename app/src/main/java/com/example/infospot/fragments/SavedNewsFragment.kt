package com.example.infospot.fragments

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infospot.Adapters.SavedAndSearchNewsAdapter
import com.example.infospot.R
import com.example.infospot.UI.ArticleWebViewActivity
import com.example.infospot.UI.NewsActivity
import com.example.infospot.UI.NewsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.fragment_saved_news.*

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var savedAndSearchNewsAdapter: SavedAndSearchNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

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

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                ).addSwipeRightBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red_400
                    )
                )
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_outline_24)
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

        exploreNews.setOnClickListener {
            (activity as NewsActivity).fragmentViewPager.currentItem = 1
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(savedNewsRecyclerView)
        }

        deleteAll.setOnClickListener {
            confirmDeleteAlert()
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner, { articles ->
            if (articles.isEmpty()) {
                saveIllustration.visibility = View.VISIBLE
            } else {
                saveIllustration.visibility = View.GONE
            }
            savedAndSearchNewsAdapter.differ.submitList(articles)
        })

        savedAndSearchNewsAdapter.setOnItemClickListener {
            val intent = Intent(context, ArticleWebViewActivity::class.java)
            intent.putExtra("articleURL", it.url)
            startActivity(intent)
        }
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

    private fun confirmDeleteAlert() =
        MaterialAlertDialogBuilder(requireContext(), R.style.CustomMaterialDialog)
            .setIcon(R.drawable.ic_trash_2)
            .setTitle("Delete All Articles")
            .setMessage("Are you sure You want to delete All saved news?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteAllArticle()
            }
            .setNegativeButton("No") { _, _ ->
            }
            .show()

}