package com.example.onlineshop.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.onlineshop.checkinternet.Common
import com.example.onlineshop.databinding.FragmentHomeBinding
import com.example.onlineshop.model.CategoryModel
import com.example.onlineshop.utils.Constants
import com.example.onlineshop.view.home.adapter.CategoryAdapter
import com.example.onlineshop.view.home.adapter.CategoryAdapterItemClick
import com.example.onlineshop.view.home.adapter.TopProductAdapter
import com.example.onlineshop.viewmodel.MainViewModel


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding!!.swipeRefresh.setOnRefreshListener {
            if (!Common.isConnectToInternet(requireContext())) {
                _binding!!.swipeRefresh.isRefreshing = false
                loadData()
            } else {
                _binding!!.swipeRefresh.isRefreshing = false
            }
        }
        viewModelObserve()
        _binding?.rvCategory?.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        loadData()
    }

    @SuppressLint("SetTextI18n")
    private fun viewModelObserve() {
        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
        viewModel.offerData.observe(requireActivity(), Observer {
            _binding!!.carouselView.setImageListener { position, imageView ->
                Glide
                    .with(imageView)
                    .load(Constants.OFFER_IMAGE + it[position].image)
                    .into(imageView)
            }
            _binding!!.carouselView.pageCount = it.size
        })

        viewModel.categoryData.observe(requireActivity(), Observer {
            _binding!!.rvCategory.adapter = CategoryAdapter(it, object : CategoryAdapterItemClick {
                override fun onclickItem(item: CategoryModel) {
                    viewModel.getCategoryByProducts(item.id)
                }
            })
            _binding!!.categoryAllSize.text = "See all (${it.size})"
        })
        viewModel.topProductData.observe(requireActivity(), Observer {
            _binding!!.rvTopProduct.adapter = TopProductAdapter(it)
            _binding!!.allSizeProduct.text = "See all (${it.size})"
        })
        viewModel.progress.observe(requireActivity(), Observer {
            _binding!!.swipeRefresh.isRefreshing = it
        })
    }

    private fun loadData() {
        if (Common.isConnectToInternet(requireContext())) {
            // false
            viewModel.getAllCategoryDB()
            viewModel.getAllProductsDB()
        } else {
            //true
            viewModel.getCategories()
            viewModel.getOffers()
            viewModel.getTopProducts()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

}
