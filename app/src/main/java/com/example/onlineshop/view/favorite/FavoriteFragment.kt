package com.example.onlineshop.view.favorite

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.onlineshop.R
import com.example.onlineshop.checkinternet.Common
import com.example.onlineshop.databinding.FragmentFavoriteBinding
import com.example.onlineshop.utils.LocaleManager
import com.example.onlineshop.view.home.adapter.TopProductAdapter
import com.example.onlineshop.utils.PrefUtils
import com.example.onlineshop.viewmodel.MainViewModel


class FavoriteFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoriteBinding.bind(view)
        _binding!!.swipeFavorite.setOnRefreshListener {
            if (!Common.isConnectToInternet(requireContext())) {
                _binding!!.swipeFavorite.isRefreshing = true
                loadData()
            } else {
                _binding!!.swipeFavorite.isRefreshing = false

            }
        }
        viewModel.topProductData.observe(requireActivity(), Observer {
            _binding!!.rvFavoriteByIdProduct.adapter = TopProductAdapter(it)
        })
        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
        viewModel.progress.observe(requireActivity(), Observer {
            _binding!!.swipeFavorite.isRefreshing = it
        })
        loadData()

    }

    override fun onResume() {
        super.onResume()
        if (!Common.isConnectToInternet(requireContext())) {
            _binding?.swipeFavorite?.isRefreshing = true
            loadData()
        } else {
            _binding?.swipeFavorite?.isRefreshing = false

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        LocaleManager.setLocale(context)
    }


    private fun loadData() {
        viewModel.getProductsById(PrefUtils.getFavoriteList())
    }

    companion object {

        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}
