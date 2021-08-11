package com.example.onlineshop.model.network.repository

import androidx.lifecycle.MutableLiveData
import com.example.onlineshop.model.*
import com.example.onlineshop.model.network.RestApi
import com.example.onlineshop.utils.PrefUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*


class ShopRepository {
    private val compositeDisposable = CompositeDisposable()
    fun getOffers(
        error: MutableLiveData<String>, progress: MutableLiveData<Boolean>,
        dataSuccess: MutableLiveData<List<OfferModel>>
    ) {
        progress.value = true
        compositeDisposable.add(
            RestApi.getRetrofit()!!.getOffers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ResponseModel<List<OfferModel>>>() {

                    override fun onNext(t: ResponseModel<List<OfferModel>>) {
                        progress.value = false

                        if (t.success) {
                            dataSuccess.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                })
        )

    }


    fun getCategories(
        error: MutableLiveData<String>,
        dataSuccess: MutableLiveData<List<CategoryModel>>
    ) {
        compositeDisposable.add(
            RestApi.getRetrofit()!!.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ResponseModel<List<CategoryModel>>>() {
                    override fun onNext(t: ResponseModel<List<CategoryModel>>) {
                        if (t.success) {
                            dataSuccess.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage

                    }
                })
        )
    }

    fun getTopProducts(
        error: MutableLiveData<String>,
        dataSuccess: MutableLiveData<List<TopProductModel>>
    ) {
        compositeDisposable.add(
            RestApi.getRetrofit()!!.getTopProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ResponseModel<List<TopProductModel>>>() {
                    override fun onNext(t: ResponseModel<List<TopProductModel>>) {
                        if (t.success) {
                            dataSuccess.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {
                    }

                })
        )

    }

    fun getCategoryIdProducts(
        id: Int,
        error: MutableLiveData<String>,
        dataSuccess: MutableLiveData<List<TopProductModel>>
    ) {
        compositeDisposable.add(
            RestApi.getRetrofit()!!.getCategoryIdProducts(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ResponseModel<List<TopProductModel>>>() {
                    override fun onNext(t: ResponseModel<List<TopProductModel>>) {
                        if (t.success) {
                            dataSuccess.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {
                    }

                })
        )

    }

    fun getProductByIds(
        ids: List<Int>,
        error: MutableLiveData<String>,
        dataSuccess: MutableLiveData<List<TopProductModel>>,
        progress: MutableLiveData<Boolean>
    ) {
        progress.value = true
        compositeDisposable.add(
            RestApi.getRetrofit()!!.getFavoritesByIdProduct(ProductByIdRequest(ids))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ResponseModel<List<TopProductModel>>>() {
                    override fun onNext(t: ResponseModel<List<TopProductModel>>) {
                        progress.value = false
                        if (t.success) {
                            t.data.forEach {
                                it.cardCount = PrefUtils.getCartCount(it)

                            }
                            dataSuccess.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {
                    }

                })
        )

    }
}