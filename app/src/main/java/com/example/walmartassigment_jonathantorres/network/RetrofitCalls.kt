package com.example.walmartassigment_jonathantorres.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue

object RetrofitProvider {

    val api: ApiCalls.CountriesApi by lazy { retrofit.create(ApiCalls.CountriesApi::class.java) }

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .build()
    }

    // baseUrl is required but unused since we pass full @Url at call time
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

}