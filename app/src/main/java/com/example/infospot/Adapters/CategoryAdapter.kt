package com.example.infospot.Adapters

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

        paintText(holder.itemView.categoryName, list.get(position).categoryName)

        holder.itemView.categoryName.text = list.get(position).categoryName
        holder.itemView.categoryRecyclerViewImage.setImageResource(list.get(position).drawable)
    }

    override fun getItemCount(): Int {
        return list.size
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

}