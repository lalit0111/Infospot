package com.example.infospot.Adapters

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.infospot.R
import com.example.infospot.models.Article
import kotlinx.android.synthetic.main.top_news_cardview.view.*


class TopNewsAdapter : RecyclerView.Adapter<TopNewsAdapter.myViewHolder>() {


    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.top_news_cardview, parent, false)
        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        val article = differ.currentList.get(position)
        holder.itemView.apply {
//
//            val gradientPosition = position % 5
//            topNewsCategoryCard.background =
//                resources.getDrawable(holder.gradients.get(gradientPosition))

            val paint: TextPaint = topNewsName.paint
            val width = paint.measureText(article.source.name)

            val textShader: Shader = LinearGradient(
                0F, 0F, width, topNewsName.textSize, intArrayOf(
                    Color.parseColor("#150bb8"),
                    Color.parseColor("#fc1bd1"),
                ), null, Shader.TileMode.CLAMP
            )
            topNewsName.paint.shader = textShader

            topNewsName.text = article.source.name
            topNewsTitle.text = article.title
            Glide.with(this)
                .load(article.urlToImage)
                .fitCenter()
                .into(topNewsRecyclerViewImage)

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

    private fun setBackgroundToCards() {

    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gradients = arrayOf(
            R.drawable.gradient1,
            R.drawable.gradient2,
            R.drawable.gradient3,
            R.drawable.gradient4,
            R.drawable.gradient5
        )
    }

}