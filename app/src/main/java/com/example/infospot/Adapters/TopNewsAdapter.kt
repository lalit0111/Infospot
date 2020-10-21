package com.example.infospot.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.infospot.R
import kotlinx.android.synthetic.main.top_news_cardview.view.*

class TopNewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.top_news_cardview, parent, false)

        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.topNewsCategoryCard.background =
            holder.itemView.resources.getDrawable(myViewHolder.gradients.get(position + 2))
    }

    override fun getItemCount(): Int {
        return 3
    }

    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            val gradients = arrayOf(
                R.drawable.gradient1, R.drawable.gradient2, R.drawable.gradient3,
                R.drawable.gradient4, R.drawable.gradient5
            )
        }
    }

}