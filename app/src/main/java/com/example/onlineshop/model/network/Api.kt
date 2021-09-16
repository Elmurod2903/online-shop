package com.example.onlineshop.model.network

import com.example.onlineshop.model.*
import com.example.onlineshop.model.request.MakeOrderRequest
import com.example.onlineshop.model.request.ProductByIdRequest
import com.example.onlineshop.model.request.RegisterRequest
import io.reactivex.Observable
import retrofit2.http.*

interface Api {
    @GET("get_offers")
    fun getOffers(): Observable<ResponseModel<List<OfferModel>>>

    @GET("get_categories")
    fun getCategories(): Observable<ResponseModel<List<CategoryModel>>>

    @GET("get_top_products")
    fun getTopProducts(): Observable<ResponseModel<List<TopProductModel>>>

    @GET("get_products/{category_id}")
    fun getCategoryIdProducts(@Path("category_id") categoryId: Int): Observable<ResponseModel<List<TopProductModel>>>

    @POST("get_products_by_ids")
    fun getFavoritesByIdProduct(@Body request: ProductByIdRequest): Observable<ResponseModel<List<TopProductModel>>>

    @GET("check_phone")
    fun checkPhone(@Query("phone") phone: String): Observable<ResponseModel<CheckPhoneResponse>>

    @GET("login")
    fun login(@Query("phone") phone: String, @Query("password") password: String): Observable<ResponseModel<LoginResponse>>

    @POST("register")
    fun register(@Body request: RegisterRequest): Observable<ResponseModel<Any>>

    @GET("confirm")
    fun confirm(@Query("phone") phone: String, @Query("sms_code") password: String): Observable<ResponseModel<LoginResponse>>

    @POST("make_order")
    fun makeOrder(@Body request: MakeOrderRequest): Observable<ResponseModel<Any>>
}