package com.example.onlineshop.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.onlineshop.model.CategoryModel
import com.example.onlineshop.model.TopProductModel

@Dao
interface ProductDao {
    @Query("DELETE from products")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<TopProductModel>)

    @Query("select * from products")
    fun getAllProducts(): List<TopProductModel>
}