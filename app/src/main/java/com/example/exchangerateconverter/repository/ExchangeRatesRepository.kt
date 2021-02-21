package com.example.exchangerateconverter.repository

import com.example.exchangerateconverter.api.CurrencyLayerAPI
import com.example.exchangerateconverter.db.ExchangeRatesDao
import com.example.exchangerateconverter.models.ExchangeRates
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class ExchangeRatesRepository
@Inject constructor(
    private val exchangeRatesDao: ExchangeRatesDao,
    private val currencyLayerAPI: CurrencyLayerAPI
) {
    val allExchangeRates = exchangeRatesDao.getAllExchangeRates()

    suspend fun fetchAndCacheExchangeRates() {
        try {
            val listOfSupportedCurrencies = currencyLayerAPI.getListOfSupportedCurrencies()
            val liveExchangeRates = currencyLayerAPI.getLiveExchangeRates()
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
                                exchangeRatesQuotes.getOrDefault(
                                    "${exchangeRatesSource}${currency.key}",
                                    0.0
                                )
                            )
                            exchangeRatesList.add(exchangeRate)
                        }

                        exchangeRatesDao.upsertExchangeRates(exchangeRatesList)
                    }
                }
            }
        } catch (e: Exception) {
            // Todo: Error handling
        }
    }

}