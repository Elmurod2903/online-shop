package com.example.onlineshop.view.cart.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop.R
import com.example.onlineshop.databinding.ItemCartProductsBinding
import com.example.onlineshop.model.TopProductModel
import com.example.onlineshop.utils.Constants
import com.example.onlineshop.utils.PrefUtils

class CartAdapter(private val items: MutableList<TopProductModel>) :
    RecyclerView.Adapter<CartAdapter.CartVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        val vh =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart_products, parent, false)
        return CartVH(vh, parent.context)

    }

    override fun onBindViewHolder(holder: CartVH, position: Int) {
        holder.bindCart(items[position])
    }

    override fun getItemCount() = items.size

    class CartVH(view: View, context: Context) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCartProductsBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bindCart(item: TopProductModel) {

            binding.tvName.text = item.name
            binding.tvPrice.text = "${item.price} so'm"
            binding.tvCountItem.text = item.cardCount.toString()
            Log.d("items", "bindCart: count : ${item.cardCount}")
            Glide.with(binding.imageProduct).load(Constants.OFFER_IMAGE + item.image)
                .into(binding.imageProduct)
            onClickItem(item)

        }

        private fun onClickItem(item: TopProductModel) {

            binding.ivAddItem.setOnClickListener {
                if (PrefUtils.getCartCount(item) > 0) {
                    item.cardCount++
                    PrefUtils.setCards(item)
                    binding.tvCountItem.text = item.cardCount.toString()

                }
            }
            binding.ivRemoveItem.setOnClickListener {
                if (PrefUtils.getCartCount(item) > 0) {
                    item.cardCount--
                    PrefUtils.setCards(item)
                    binding.tvCountItem.text = item.cardCount.toString()

                }

            }
        }
    }

}


