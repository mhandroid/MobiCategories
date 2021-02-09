package com.mh.android.mobicategories.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mh.android.mobicategories.BuildConfig
import com.mh.android.mobicategories.R
import com.mh.android.mobicategories.model.Product
import com.squareup.picasso.Picasso

/**
 * Created by mubarak.hussain on 06/02/21.
 */
class FoodCategoryAdapter(val context: Context, private val mutableListOf: List<Product>, private val itemClickListener: ListItemCLickListener? = null) :
    RecyclerView.Adapter<FoodCategoryAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val imgView: ImageView = itemView.findViewById(R.id.ivProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.category_list_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product: Product = mutableListOf[position]
        holder.txtTitle.text = product.name

        product.url?.let { url ->
            val finalUrl = BuildConfig.BASE_URL+url
            Log.d("MUB","url $finalUrl")
            Picasso.with(context).load(finalUrl).placeholder(R.drawable.ic_launcher_background).fit().into(holder.imgView)
        } ?: holder.imgView.setImageResource(R.drawable.ic_launcher_background)

        holder.view.setOnClickListener { itemClickListener?.onItemClick(product) }
    }

    override fun getItemCount(): Int = mutableListOf.size

    interface ListItemCLickListener {
        fun onItemClick(product: Product)
    }
}