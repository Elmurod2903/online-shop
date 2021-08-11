package com.example.onlineshop.model.network

import com.example.onlineshop.model.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

}