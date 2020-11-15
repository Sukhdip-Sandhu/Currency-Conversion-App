package com.example.exchangerateconverter.repository

import com.example.exchangerateconverter.api.RetrofitInstance
import com.example.exchangerateconverter.db.ExchangeRatesDatabase
import com.example.exchangerateconverter.models.ExchangeRates
import com.example.exchangerateconverter.models.LiveExchangeRatesResponse
import com.example.exchangerateconverter.models.SupportedCurrenciesResponse
import com.example.exchangerateconverter.pref.PreferenceProvider
import retrofit2.Response

class ExchangeRatesRepository(
    private val db: ExchangeRatesDatabase
) {

    val allExchangeRates = db.getExchangeRateDao().getAllExchangeRates()

    suspend fun upsertExchangeRates(exchangeRates: List<ExchangeRates>) =
        db.getExchangeRateDao().upsertExchangeRates(exchangeRates)

    suspend fun getListOfSupportedCurrencies(): Response<SupportedCurrenciesResponse> =
        RetrofitInstance.api.getListOfSupportedCurrencies()

    suspend fun getLiveExchangeRates(): Response<LiveExchangeRatesResponse> =
        RetrofitInstance.api.getLiveExchangeRates()
}