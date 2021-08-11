package com.example.onlineshop.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.onlineshop.R
import com.example.onlineshop.databinding.ActivityMainBinding
import com.example.onlineshop.screen.cart.CartFragment
import com.example.onlineshop.screen.favorite.FavoriteFragment
import com.example.onlineshop.screen.home.HomeFragment
import com.example.onlineshop.screen.profile.ProfileFragment
import com.example.onlineshop.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private val cartFragment = CartFragment.newInstance()
    private val profileFragment = ProfileFragment.newInstance()
    private val homeFragment = HomeFragment.newInstance()
    private val favoriteFragment = FavoriteFragment.newInstance()
    private var activeFragment: Fragment = homeFragment

    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewModel = MainViewModel()

        viewModelObserver()
        generateSupportFragment()
        viewBinding.bottonNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(homeFragment)
                        .commit()
                    activeFragment = homeFragment
                }
                R.id.cart -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(cartFragment)
                        .commit()
                    activeFragment = cartFragment

                }
                R.id.favorite -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(favoriteFragment)
                        .commit()
                    activeFragment = favoriteFragment

                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(profileFragment)
                        .commit()
                    activeFragment = profileFragment

                }
            }
            true

        }

        loadData()

    }

    private fun viewModelObserver() {
        viewModel.topProductData.observe(this, Observer {
            viewModel.insertAllProductsDB(it)
        })

        viewModel.categoryData.observe(this, Observer {
            viewModel.insertAllCategoryDB(it)
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun loadData() {
        viewModel.getTopProducts()
        viewModel.getCategories()
    }

    private fun generateSupportFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fillContainer, homeFragment, homeFragment.tag).hide(homeFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fillContainer, favoriteFragment, favoriteFragment.tag).hide(favoriteFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fillContainer, cartFragment, cartFragment.tag).hide(cartFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fillContainer, profileFragment, profileFragment.tag).hide(profileFragment)
            .commit()
        supportFragmentManager.beginTransaction().show(homeFragment).commit()


    }
}