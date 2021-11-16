package com.example.onlineshop.app

import android.content.Context
import android.content.res.Configuration
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.onlineshop.db.MyDatabase
import com.example.onlineshop.utils.LocaleManager
import com.orhanobut.hawk.Hawk

class App : MultiDexApplication() {

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleManager.setLocale(this)
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        Hawk.init(this).build()
        MyDatabase.initDatabase(this)
    }
}