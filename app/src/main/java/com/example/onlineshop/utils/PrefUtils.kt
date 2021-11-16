package com.example.onlineshop.utils

import android.net.Uri
import android.nfc.cardemulation.HostApduService
import com.example.onlineshop.model.CartModel
import com.example.onlineshop.model.TopProductModel
import com.orhanobut.hawk.Hawk

object PrefUtils {
    private const val PREF_FAVORITE = "pref_utils"
    private const val PREF_CART = "pref_cart"
    private const val PREF_TOKEN = "pref_token"
    private const val PREF_NAME = "pref_name"
    private const val PREF_GMAIL_NAME = "pref_gmail_name"
    private const val PREF_NUMBER = "pref_number"
    private const val PREF_FCM_TOKEN = "pref_fcm_token"
    private const val PREF_USER_IMAGE = "pref_user_image"

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
        return items.firstOrNull { it.product_id == item.id }?.count ?: 0
    }

    fun setToken(value: String) {
        Hawk.put(PREF_TOKEN, value)
    }

    fun getToken(): String {
        return Hawk.get(PREF_TOKEN, "")
    }

    fun setFCMToken(value: String) {
        Hawk.put(PREF_FCM_TOKEN, value)
    }

    fun getFCMToken(): String {
        return Hawk.get(PREF_FCM_TOKEN, "")
    }

    fun setFullName(value: String) {
        Hawk.put(PREF_NAME, value)
    }

    fun setNumber(value: String) {
        Hawk.put(PREF_NUMBER, value)
    }

    fun getFullName(): String {
        return Hawk.get(PREF_NAME, "")
    }

    fun getNumber(): String {
        return Hawk.get(PREF_NUMBER, "")
    }

    fun setNewGmail(value: String) {
        Hawk.put(PREF_GMAIL_NAME, value)
    }
    fun getNewGmail(): String {
        return Hawk.get(PREF_GMAIL_NAME, "")
    }

    fun setImageUser(value: Uri) {
        Hawk.put(PREF_USER_IMAGE, value)
    }

    fun getImageUser(): Uri {
        return Hawk.get(PREF_USER_IMAGE)
    }

}