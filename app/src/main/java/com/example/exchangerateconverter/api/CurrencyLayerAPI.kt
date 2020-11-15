package com.example.exchangerateconverter.api

import com.example.exchangerateconverter.models.LiveExchangeRatesResponse
import com.example.exchangerateconverter.models.SupportedCurrenciesResponse
import com.example.exchangerateconverter.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyLayerAPI {

    @GET("list")
    suspend fun getListOfSupportedCurrencies(
        @Query("access_key")
        apiKey: String = API_KEY
    ): Response<SupportedCurrenciesResponse>

    @GET("live")
    suspend fun getLiveExchangeRates(
        @Query("access_key")
        apiKey: String = API_KEY
    ): Response<LiveExchangeRatesResponse>

}