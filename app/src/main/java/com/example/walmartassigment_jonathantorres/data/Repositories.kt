package com.example.walmartassigment_jonathantorres.data

import com.example.walmartassigment_jonathantorres.network.RetrofitProvider

class Repositories {

    suspend fun fetchCountries(): Result<List<Country>> = try {
        Result.success(RetrofitProvider.api.getCountries())
    } catch (e: Exception) {
        Result.failure(e)
    }

}