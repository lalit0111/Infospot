package com.example.infospot.Adapters

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.infospot.R
import com.example.infospot.UI.NewsViewModel
import com.example.infospot.models.Article
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.top_news_cardview.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TopNewsAdapter(val viewModel: NewsViewModel) :
    RecyclerView.Adapter<TopNewsAdapter.myViewHolder>() {

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.top_news_cardview, parent, false)
        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        val article = differ.currentList[position]
        holder.itemView.apply {

            paintText(topNewsName, article.source.name)

            topNewsName.text = article.source.name
            topNewsTitle.text = article.title

            loadImage(context, article.urlToImage, topNewsRecyclerViewImage)

            viewModel.viewModelScope.launch(Dispatchers.Main) {
                if (viewModel.isArticleSaved(article)) {
                    saveArticleButton.background =
                        resources.getDrawable(R.drawable.ic_baseline_check_24)
                }
            }

            saveArticleButton.setOnClickListener {
                viewModel.viewModelScope.launch(Dispatchers.Main) {
                    if (viewModel.isArticleSaved(article)) {
                        Snackbar.make(
                            holder.itemView,
                            "Article already saved!!",
                            Snackbar.LENGTH_SHORT
                        ).show()

                    } else {
                        saveArticleButton.background =
                            resources.getDrawable(R.drawable.ic_baseline_check_24)
                        Snackbar.make(
                            holder.itemView,
                            "Article saved successfully!!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        viewModel.saveArticle(article)
                        notifyDataSetChanged()
                    }
                }
            }

            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listner: (Article) -> Unit) {
        onItemClickListener = listner
    }


    private fun paintText(textView: TextView, text: String) {

        val paint: TextPaint = textView.paint
        val width = paint.measureText(text)

        val textShader: Shader = LinearGradient(
            0F, 0F, width, textView.textSize, intArrayOf(
                Color.parseColor("#150bb8"),
                Color.parseColor("#fc1bd1"),
            ), null, Shader.TileMode.CLAMP
        )
        textView.paint.shader = textShader

    }

    private fun loadImage(context: Context, url: String, image: ImageView) {
        Glide.with(context)
            .load(url)
            .fitCenter()
            .placeholder(R.drawable.ic_undraw_page_not_found_su7k)
            .into(image)
    }


}