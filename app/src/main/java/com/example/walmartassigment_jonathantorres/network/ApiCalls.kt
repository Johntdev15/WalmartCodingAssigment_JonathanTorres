package com.example.walmartassigment_jonathantorres.network

import com.example.walmartassigment_jonathantorres.data.Country
import retrofit2.http.GET

interface ApiCalls {

    interface CountriesApi {
        @GET("peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json")
        suspend fun getCountries(): List<Country>
    }

}