package com.example.onlineshop.screen.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentCartBinding
import com.example.onlineshop.model.TopProductModel
import com.example.onlineshop.screen.MakeOrderActivity
import com.example.onlineshop.screen.cart.adapter.CartAdapter
import com.example.onlineshop.utils.Constants
import com.example.onlineshop.utils.PrefUtils
import com.example.onlineshop.viewmodel.MainViewModel
import java.io.Serializable


class CartFragment : Fragment() {
    lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(view)
        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
        viewModel.topProductData.observe(requireActivity(), Observer {
            val adapter = CartAdapter(it as MutableList<TopProductModel>)
            binding.rvCart.adapter = adapter


        })
        binding.swipeCart.setOnRefreshListener {
            loadData()
        }
        viewModel.progress.observe(requireActivity(), Observer {
            binding.swipeCart.isRefreshing = it
        })
        binding.btnMakeOrder.setOnClickListener {
            val intent = Intent(view.context, MakeOrderActivity::class.java)
            intent.putExtra(
                Constants.PRODUCT_DATA,
                (viewModel.topProductData.value)
                        as Serializable
            )
            startActivity(intent)
        }

        loadData()

    }

    private fun loadData() {
        viewModel.getProductsById(PrefUtils.getCartList().map { it.product_id })

    }

    companion object {
        @JvmStatic
        fun newInstance() = CartFragment()
    }
}