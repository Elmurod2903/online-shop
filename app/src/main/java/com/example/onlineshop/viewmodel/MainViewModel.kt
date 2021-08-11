package com.example.onlineshop.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlineshop.db.MyDatabase
import com.example.onlineshop.model.CategoryModel
import com.example.onlineshop.model.OfferModel
import com.example.onlineshop.model.TopProductModel
import com.example.onlineshop.model.network.repository.ShopRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class MainViewModel : ViewModel() {
    val error = MutableLiveData<String>()

    var offerData = MutableLiveData<List<OfferModel>>()
    var categoryData = MutableLiveData<List<CategoryModel>>()
    val topProductData = MutableLiveData<List<TopProductModel>>()
    val progress = MutableLiveData<Boolean>()

    private val shopRepository = ShopRepository()

    fun getOffers() {
        shopRepository.getOffers(error, progress, offerData)
    }

    fun getCategories() {
        shopRepository.getCategories(error, categoryData)
    }

    fun getTopProducts() {
        shopRepository.getTopProducts(error, topProductData)
    }

    fun getCategoryByProducts(id: Int) {
        shopRepository.getCategoryIdProducts(id, error, topProductData)
    }

    fun getProductsById(ids: List<Int>) {
        shopRepository.getProductByIds(ids, error, topProductData, progress)
    }

    fun insertAllProductsDB(items: List<TopProductModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            MyDatabase.getDatabase().productDao().deleteAll()
            MyDatabase.getDatabase().productDao().insertAll(items)
        }

    }

    fun insertAllCategoryDB(items: List<CategoryModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            MyDatabase.getDatabase().categoryDao().deleteAll()
            MyDatabase.getDatabase().categoryDao().insertAll(items)
        }

    }

    fun getAllProductsDB() {
        CoroutineScope(Dispatchers.Main).launch {
            topProductData.value = withContext(Dispatchers.IO) {
                MyDatabase.getDatabase().productDao().getAllProducts()
            }

        }
    }

    fun getAllCategoryDB() {
        CoroutineScope(Dispatchers.Main).launch {
            categoryData.value = withContext(Dispatchers.IO) {
                MyDatabase.getDatabase().categoryDao().getAllCategories()
            }

        }

    }

}
