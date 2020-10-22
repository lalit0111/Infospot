package com.example.infospot.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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

//        val article = differ.currentList.get(position)
        holder.itemView.apply {
            topNewsCategoryCard.background =
                resources.getDrawable(holder.gradients.get(position + 2))
            setOnClickListener {
//                onItemClickListener?.let { it(article) }
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listner: (Article) -> Unit) {
        onItemClickListener = listner
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