package com.example.onlineshop.model.network

import com.example.onlineshop.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RestApi {
    private var api: Api? = null
    fun getRetrofit(): Api? {
        val retrofit: Retrofit?
        if (api == null) {
            val okHttpClient = OkHttpClient.Builder()
            okHttpClient.addInterceptor(AppInterceptor())

            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            api = retrofit.create(Api::class.java)
        }
        return api
    }
}