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
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.infospot.R
import com.example.infospot.models.Article
import kotlinx.android.synthetic.main.search_and_save_card.view.*

class SavedAndSearchNewsAdapter :
    RecyclerView.Adapter<SavedAndSearchNewsAdapter.savedNewsViewHoler>() {

    inner class savedNewsViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): savedNewsViewHoler {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.search_and_save_card, parent, false)
        return savedNewsViewHoler(itemView)
    }

    override fun onBindViewHolder(holder: savedNewsViewHoler, position: Int) {

        val article = differ.currentList.get(position)

        holder.itemView.apply {
            paintText(holder.itemView.savedAndSearchNewsName, "something")
            savedAndSearchNewsName.text = article.source.name
            savedAndSearchNewsTitle.text = article.title
            dateTextView.text = formatDate(article.publishedAt)
            loadImage(context, article.urlToImage, savedAndSearchNewsImage)


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
            .placeholder(R.drawable.nopath)
            .into(image)
    }

    private fun formatDate(d: String): String {
        val year = d.substring(0, 4)
        val month = d.substring(5, 7)
        val date = d.substring(8, 10)
        val monthName = when (month.toInt()) {
            1 -> "Jan"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "May"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Aug"
            9 -> "Sep"
            10 -> "Oct"
            11 -> "Nov"
            12 -> "Dec"
            else -> ""
        }
        return "$date $monthName $year"
    }
}