package com.example.vk_test

import android.provider.DocumentsContract
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitController {

    @GET("/products")
    fun getProduct(@Query("skip") access_token: String, @Query("limit") v: String): Call<ProductClassList>
}