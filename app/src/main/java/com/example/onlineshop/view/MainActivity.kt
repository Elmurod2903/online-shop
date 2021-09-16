package com.example.onlineshop.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import com.example.onlineshop.R
import com.example.onlineshop.checkinternet.Common
import com.example.onlineshop.checkinternet.NetworkChangeListener
import com.example.onlineshop.databinding.ActivityMainBinding

import com.example.onlineshop.view.cart.CartFragment
import com.example.onlineshop.view.changelanguage.ChangeLanguageFragment
import com.example.onlineshop.view.favorite.FavoriteFragment
import com.example.onlineshop.view.home.HomeFragment
import com.example.onlineshop.view.profile.ProfileFragment
import com.example.onlineshop.utils.LocaleManager
import com.example.onlineshop.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {


    private lateinit var viewBinding: ActivityMainBinding
    private val cartFragment = CartFragment.newInstance()
    private val profileFragment = ProfileFragment.newInstance()
    private val homeFragment = HomeFragment.newInstance()
    private val favoriteFragment = FavoriteFragment.newInstance()
    private var activeFragment: Fragment = homeFragment
    private var broadcastReceiver: BroadcastReceiver? = null

    private lateinit var viewModel: MainViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        broadcastReceiver = NetworkChangeListener()
        if (Common.isConnectToInternet(this)) {
            showInternetDialog()
        }
        viewModel = MainViewModel()

        viewModelObserver()
        generateSupportFragment()
        viewBinding.bottonNavigationView.setOnItemSelectedListener { item ->
            navigationSelected(item)
            true

        }
        createProfile()
        loadSettings()
        loadData()

    }

    private fun showInternetDialog() {

        //init dialog
        val alertDialog = Dialog(this)
        // set content view
        alertDialog.setContentView(R.layout.no_internet_dialog)

        // set outside view
        alertDialog.setCanceledOnTouchOutside(false)

        //set transparent background
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // set animation
        alertDialog.window?.attributes?.windowAnimations = android.R.style.Animation_Dialog
        //init variable
        val btnTry = alertDialog.findViewById<View>(R.id.btn_try_again)
        btnTry.setOnClickListener {
            recreate()
        }
        val btnClose = alertDialog.findViewById<View>(R.id.btn_close)
        btnClose.setOnClickListener {
            alertDialog.dismiss()
            Toast.makeText(this, "offline connection", Toast.LENGTH_SHORT).show()
        }
        // show dialog
        alertDialog.show()

    }


    private fun loadSettings() {
        viewBinding.ivSetting.setOnClickListener {
            val fragmentChangeLanguage = ChangeLanguageFragment.newInstance()
            fragmentChangeLanguage.show(supportFragmentManager, fragmentChangeLanguage.tag)
        }
    }

    private fun createProfile() {
        viewBinding.userProfile.setOnClickListener {
            initProfile()
        }

    }

    private fun initProfile() {
        supportFragmentManager.beginTransaction().hide(activeFragment).show(profileFragment)
            .commit()
        activeFragment = profileFragment
        viewBinding.tvTitleNav.text = resources.getString(R.string.profile)
        viewBinding.cardUser.visibility = View.GONE
        viewBinding.ivSetting.visibility = View.VISIBLE
        viewBinding.bottonNavigationView.selectedItemId = R.id.profile
    }

    private fun navigationSelected(item: MenuItem) {
        when (item.itemId) {
            R.id.home -> {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(homeFragment)
                    .commit()
                activeFragment = homeFragment
                viewBinding.tvTitleNav.text = resources.getString(R.string.home)
                viewBinding.cardUser.visibility = View.VISIBLE
                viewBinding.ivSetting.visibility = View.GONE

            }
            R.id.cart -> {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(cartFragment)
                    .commit()
                activeFragment = cartFragment
                viewBinding.tvTitleNav.text = resources.getString(R.string.cart)
                viewBinding.cardUser.visibility = View.VISIBLE
                viewBinding.ivSetting.visibility = View.GONE


            }
            R.id.favorite -> {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(favoriteFragment)
                    .commit()
                activeFragment = favoriteFragment
                viewBinding.tvTitleNav.text = resources.getString(R.string.favorite)
                viewBinding.cardUser.visibility = View.VISIBLE
                viewBinding.ivSetting.visibility = View.GONE


            }
            R.id.profile -> {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(profileFragment)
                    .commit()
                activeFragment = profileFragment
                viewBinding.tvTitleNav.text = resources.getString(R.string.profile)
                viewBinding.cardUser.visibility = View.GONE
                viewBinding.ivSetting.visibility = View.VISIBLE


            }

        }

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

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }

    override fun onStart() {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(broadcastReceiver, intentFilter)
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(broadcastReceiver)
        super.onStop()
    }

}
