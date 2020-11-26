package com.example.infospot.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.infospot.R
import com.example.infospot.models.Category
import kotlinx.android.synthetic.main.category_cardview.view.*

class CategoryAdapter(val list: ArrayList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.myViewHolder>() {

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.category_cardview, parent, false)
        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

//        paintText(holder.itemView.categoryName, list.get(position).categoryName)
        holder.itemView.categoryName.text = list.get(position).categoryName
        holder.itemView.categoryRecyclerViewImage.setImageResource(list.get(position).drawable)
        holder.itemView.mainCard.setCardBackgroundColor(Color.parseColor(list.get(position).categoryColor))
        holder.itemView.smallCard.setCardBackgroundColor(Color.parseColor(list.get(position).categoryColor))
        holder.itemView.linear.setBackgroundColor(Color.parseColor(list.get(position).categoryColor))

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(
                    list.get(position).categoryName,
                    list.get(position).categoryColor
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private var onItemClickListener: ((category: String, color: String) -> Unit)? = null

    fun setOnItemClickListener(listner: (category: String, color: String) -> Unit) {
        onItemClickListener = listner
    }

}