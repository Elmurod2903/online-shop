package com.example.onlineshop.model.repository

import androidx.lifecycle.MutableLiveData
import com.example.onlineshop.model.*
import com.example.onlineshop.model.network.RestApi
import com.example.onlineshop.model.request.MakeOrderRequest
import com.example.onlineshop.model.request.ProductByIdRequest
import com.example.onlineshop.model.request.RegisterRequest
import com.example.onlineshop.utils.PrefUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


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

    fun checkPhone(
        phone: String,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<CheckPhoneResponse>
    ) {
        progress.value = true
        compositeDisposable.add(
            RestApi.getRetrofit()!!.checkPhone(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ResponseModel<CheckPhoneResponse>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: ResponseModel<CheckPhoneResponse>) {
                        progress.value = false
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                })
        )
    }

    fun registration(
        fullname: String,
        phone: String,
        password: String,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<Boolean>
    ) {
        progress.value = true
        compositeDisposable.add(
            RestApi.getRetrofit()!!.register(RegisterRequest(fullname, phone, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ResponseModel<Any>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: ResponseModel<Any>) {
                        progress.value = false
                        if (t.success) {
                            success.value = true
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                })
        )
    }

    fun confirmUser(
        phone: String,
        sms_code: String,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<LoginResponse>
    ) {
        progress.value = true
        compositeDisposable.add(
            RestApi.getRetrofit()!!.confirm(phone, sms_code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ResponseModel<LoginResponse>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: ResponseModel<LoginResponse>) {
                        progress.value = false
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                })
        )
    }

    fun login(
        phone: String,
        password: String,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<LoginResponse>
    ) {
        progress.value = true
        compositeDisposable.add(
            RestApi.getRetrofit()!!.login(phone, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ResponseModel<LoginResponse>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: ResponseModel<LoginResponse>) {
                        progress.value = false
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                })
        )
    }

    fun makeOrder(
        products: List<CartModel>,
        lat: Double,
        lon: Double,
        comment: String,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<Boolean>
    ) {
        progress.value = true
        compositeDisposable.add(
            RestApi.getRetrofit()!!
                .makeOrder(MakeOrderRequest(products, "delivery", "", lat, lon, comment))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ResponseModel<Any>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: ResponseModel<Any>) {
                        progress.value = false
                        if (t.success) {
                            success.value = true
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                })
        )
    }


}