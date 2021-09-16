package com.example.onlineshop.view.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop.R
import com.example.onlineshop.databinding.ItemTopProductBinding
import com.example.onlineshop.model.TopProductModel
import com.example.onlineshop.view.DetailActivity
import com.example.onlineshop.utils.Constants

class TopProductAdapter(private val productList: List<TopProductModel>) :
    RecyclerView.Adapter<TopProductAdapter.TopProductVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductVH {
        val vh =
            LayoutInflater.from(parent.context).inflate(R.layout.item_top_product, parent, false)
        return TopProductVH(vh, parent.context)
    }

    override fun onBindViewHolder(holder: TopProductVH, position: Int) {
        holder.bindProduct(productList[position])
    }

    override fun getItemCount() = productList.size

    inner class TopProductVH(private val view: View, context: Context) :
        RecyclerView.ViewHolder(view) {
        private val binding = ItemTopProductBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bindProduct(item: TopProductModel) {
            view.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra(Constants.PRODUCT_DATA, item)
                it.context.startActivity(intent)
            }
            binding.tvName.text = item.name
            binding.tvPrice.text = "${item.price} so'm"
            Glide.with(binding.imageProduct).load(Constants.OFFER_IMAGE + item.image)
                .into(binding.imageProduct)
        }
    }
}