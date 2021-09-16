package com.example.onlineshop.checkinternet

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import com.example.onlineshop.view.MainActivity

class Common {
    companion object {
        fun isConnectToInternet(context: Context): Boolean {

            val connectManager: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectManager.activeNetworkInfo

            return (networkInfo == null || !networkInfo.isConnected || !networkInfo.isAvailable)

        }

    }
}