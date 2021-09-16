package com.example.onlineshop.view.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentFavoriteBinding
import com.example.onlineshop.view.home.adapter.TopProductAdapter
import com.example.onlineshop.utils.PrefUtils
import com.example.onlineshop.viewmodel.MainViewModel


class FavoriteFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)
        binding.swipeFavorite.setOnRefreshListener {
            loadData()
        }
        viewModel.topProductData.observe(requireActivity(), Observer {
            binding.rvFavoriteByIdProduct.adapter = TopProductAdapter(it)
        })
        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
        viewModel.progress.observe(requireActivity(), Observer {
            binding.swipeFavorite.isRefreshing = it
        })
        loadData()

    }

    private fun loadData() {
        viewModel.getProductsById(PrefUtils.getFavoriteList())
    }


    companion object {

        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}
