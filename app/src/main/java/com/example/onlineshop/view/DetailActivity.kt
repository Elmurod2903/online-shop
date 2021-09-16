package com.example.onlineshop.view

import android.annotation.SuppressLint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.onlineshop.R
import com.example.onlineshop.databinding.ActivityDetailBinding
import com.example.onlineshop.model.TopProductModel
import com.example.onlineshop.utils.Constants
import com.example.onlineshop.utils.PrefUtils

class DetailActivity : AppCompatActivity() {
    private var itemProduct: TopProductModel? = null
    lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardBack.setOnClickListener {
            finish()
        }
        binding.cardFavorite.setOnClickListener {
            PrefUtils.setFavorites(itemProduct!!)
            favoriteChecked()
        }

        itemProduct = intent.getSerializableExtra(Constants.PRODUCT_DATA) as TopProductModel

        binding.tvTitle.text = itemProduct!!.name
        binding.tvDetailPrice.text = "${itemProduct!!.price} so'm"
        Glide.with(this).load(Constants.OFFER_IMAGE + itemProduct!!.image).into(binding.ivFavorite)

        if (PrefUtils.getCartCount(itemProduct!!) > 0) {
            binding.btnCart.visibility = View.INVISIBLE
        }

        favoriteChecked()
        binding.btnCart.setOnClickListener {
            itemProduct!!.cardCount = 1
            PrefUtils.setCards(itemProduct!!)
            Toast.makeText(this, "Product added to Cart", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    private fun favoriteChecked() {
        if (PrefUtils.checkFavorite(itemProduct!!)) {
            binding.favorite.setImageResource(R.drawable.ic_heart_2)
        } else {
            binding.favorite.setImageResource(R.drawable.ic_heart_1)

        }
    }
}