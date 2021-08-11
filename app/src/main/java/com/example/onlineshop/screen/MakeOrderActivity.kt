package com.example.onlineshop.screen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.onlineshop.MapsActivity
import com.example.onlineshop.R
import com.example.onlineshop.databinding.ActivityMakeOrderBinding
import com.example.onlineshop.model.AddressModel
import com.example.onlineshop.model.TopProductModel
import com.example.onlineshop.utils.Constants
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MakeOrderActivity : AppCompatActivity() {
    lateinit var binding: ActivityMakeOrderBinding
    private var addressItem: AddressModel? = null
    lateinit var items: List<TopProductModel>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        binding = ActivityMakeOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        items = intent.getSerializableExtra(Constants.PRODUCT_DATA) as List<TopProductModel>

        binding.cardBack.setOnClickListener { finish() }
        binding.etAddress.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
        binding.tvPriceAmount.text = "${
            items.map { (it.cardCount).toLong() * (it.price.replace(" ", "")).toLong() }.sum()
        } so'm "
        binding.etComment.setHintTextColor(ContextCompat.getColor(this, R.color.purple_500))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    fun onEventAddress(address: AddressModel) {
        addressItem = address
        binding.etAddress.setText("${address.latitude},${address.longitude}")
    }
}