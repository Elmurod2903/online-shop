package com.example.onlineshop.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "products")
data class TopProductModel(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val id: Int,
    val name: String,
    val price: String,
    val category_id: String,
    val image: String,
    var cardCount: Int
) : Serializable