package com.example.exchangerateconverter.repository

import androidx.lifecycle.LiveData
import com.example.exchangerateconverter.api.RetrofitInstance
import com.example.exchangerateconverter.db.ExchangeRatesDatabase
import com.example.exchangerateconverter.models.ExchangeRates
import com.example.exchangerateconverter.models.LiveExchangeRatesResponse
import com.example.exchangerateconverter.models.SupportedCurrenciesResponse
import kotlinx.coroutines.delay
import retrofit2.Response
import java.lang.Exception

class ExchangeRatesRepository(
        private val db: ExchangeRatesDatabase
) {

    val allExchangeRates = db.getExchangeRateDao().getAllExchangeRates()

    suspend fun fetchAndCacheExchangeRates() {
        try {
            val listOfSupportedCurrencies = RetrofitInstance.api.getListOfSupportedCurrencies()
            val liveExchangeRates = RetrofitInstance.api.getLiveExchangeRates()
            if (listOfSupportedCurrencies.isSuccessful && liveExchangeRates.isSuccessful) {
                listOfSupportedCurrencies.body()?.let { supportedCurrencies ->
                    liveExchangeRates.body()?.let { exchangeRates ->

                        val exchangeRatesList: MutableList<ExchangeRates> = mutableListOf()
                        val currencies = supportedCurrencies.currencies
                        val exchangeRatesQuotes = exchangeRates.quotes
                        val exchangeRatesSource = exchangeRates.source

                        for (currency in currencies) {
                            val exchangeRate = ExchangeRates(
                                    currency.key,
                                    currency.value,
                                    exchangeRatesQuotes.getOrDefault("${exchangeRatesSource}${currency.key}", 0.0)
                            )
                            exchangeRatesList.add(exchangeRate)
                        }

                        db.getExchangeRateDao().upsertExchangeRates(exchangeRatesList)
                    }
                }
            }
        } catch (e: Exception) {
            // Todo: Error handling
        }
    }

}