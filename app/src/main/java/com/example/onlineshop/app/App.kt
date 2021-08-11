package com.example.onlineshop.app

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.onlineshop.db.MyDatabase
import com.orhanobut.hawk.Hawk

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        Hawk.init(this).build()
        MyDatabase.initDatabase(this)
    }
}