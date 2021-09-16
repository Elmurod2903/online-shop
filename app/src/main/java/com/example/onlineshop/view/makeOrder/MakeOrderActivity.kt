package com.example.onlineshop.view.makeOrder

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.onlineshop.MapsActivity
import com.example.onlineshop.databinding.ActivityMakeOrderBinding
import com.example.onlineshop.model.AddressModel
import com.example.onlineshop.model.CartModel
import com.example.onlineshop.model.TopProductModel
import com.example.onlineshop.utils.Constants
import com.example.onlineshop.viewmodel.MainViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MakeOrderActivity : AppCompatActivity() {
    lateinit var binding: ActivityMakeOrderBinding
    private var addressItem: AddressModel? = null
    lateinit var items: List<TopProductModel>
    lateinit var viewModel: MainViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(this, Observer {
            binding.flProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        EventBus.getDefault().register(this)

        items = intent.getSerializableExtra(Constants.PRODUCT_DATA) as List<TopProductModel>

        binding.cardBack.setOnClickListener { finish() }
        binding.etAddress.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
        binding.tvPriceAmount.text = "${
            items.map { (it.cardCount).toLong() * (it.price.replace(" ", "")).toLong() }.sum()
        } so'm "

        binding.btnMake.setOnClickListener {
            viewModel.makeOrder(
                items.map { CartModel(it.id, it.cardCount) },
                addressItem?.latitude ?: 0.0,
                addressItem?.longitude ?: 0.0,
                binding.etComment.toString()
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventAddress(address: AddressModel) {
        addressItem = address
        binding.etAddress.setText("${address.latitude},${address.longitude}")
    }
}