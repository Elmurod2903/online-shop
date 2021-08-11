package com.example.onlineshop.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.onlineshop.model.CategoryModel
import com.example.onlineshop.model.TopProductModel

@Database(entities = [CategoryModel::class, TopProductModel::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        private var instance: MyDatabase? = null

        fun initDatabase(context: Context) {
            if (instance == null) {
                synchronized(MyDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "online_shop_db"
                    ).build()
                }
            }

        }

        fun getDatabase(): MyDatabase {
            return instance!!
        }
    }
}