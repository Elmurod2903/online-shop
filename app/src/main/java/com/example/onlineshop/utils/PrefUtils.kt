package com.example.onlineshop.utils

import com.example.onlineshop.model.CartModel
import com.example.onlineshop.model.TopProductModel
import com.orhanobut.hawk.Hawk

object PrefUtils {
    private const val PREF_FAVORITE = "pref_utils"
    private const val PREF_CART = "pref_cart"


    fun setFavorites(item: TopProductModel) {
        val items = Hawk.get(PREF_FAVORITE, arrayListOf<Int>())
        if (items.firstOrNull { it == item.id } != null) {
            items.remove(item.id)
        } else {
            items.add(item.id)
        }

        Hawk.put(PREF_FAVORITE, items)
    }

    fun getFavoriteList(): ArrayList<Int> {
        return Hawk.get(PREF_FAVORITE, arrayListOf<Int>())
    }

    fun checkFavorite(item: TopProductModel): Boolean {
        val items = Hawk.get(PREF_FAVORITE, arrayListOf<Int>())
        return items.firstOrNull { it == item.id } != null
    }

    fun setCards(item: TopProductModel) {
        val items = Hawk.get<ArrayList<CartModel>>(PREF_CART, arrayListOf())
        val cart = items.firstOrNull { it.product_id == item.id }
        if (cart != null) {
            if (item.cardCount > 0) {
                cart.count = item.cardCount
            } else {
                items.remove(cart)
            }
        } else {
            val newCart = CartModel(item.id, item.cardCount)
            items.add(newCart)

        }
        Hawk.put(PREF_CART, items)

    }

    fun getCartList(): ArrayList<CartModel> {
        return Hawk.get(PREF_CART, arrayListOf())

    }

    fun getCartCount(item: TopProductModel): Int {
        val items = Hawk.get<ArrayList<CartModel>>(PREF_CART, arrayListOf())
        return items.firstOrNull { it.product_id == item.id }?.count ?:0
    }

}