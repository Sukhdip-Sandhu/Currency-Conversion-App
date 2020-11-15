package com.example.exchangerateconverter.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.exchangerateconverter.models.ExchangeRates
import com.example.exchangerateconverter.models.LiveExchangeRatesResponse
import com.example.exchangerateconverter.models.SupportedCurrenciesResponse
import com.example.exchangerateconverter.repository.ExchangeRatesRepository
import kotlinx.coroutines.launch

class ExchangeRatesViewModel(
    private val exchangeRatesRepository: ExchangeRatesRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _allExchangeRates = exchangeRatesRepository.allExchangeRates
    private val _userCurrencyInput = MutableLiveData<Double>()
    private val _progress = MutableLiveData<Boolean>()

    init {
        fetchAndCacheData()
    }

    fun getAllExchangeRates(): LiveData<List<ExchangeRates>> {
        return _allExchangeRates
    }

    fun getUserCurrencyInput(): LiveData<Double> {
        return _userCurrencyInput
    }

    fun updateUserCurrency(number: Double) {
        _userCurrencyInput.value = number
    }

    fun getProgress(): LiveData<Boolean> {
        return _progress
    }

    fun refreshData() {
        fetchAndCacheData()
    }

    private fun fetchAndCacheData() = viewModelScope.launch {
        _progress.postValue(true)
        val listOfSupportedCurrencies = exchangeRatesRepository.getListOfSupportedCurrencies()
        val liveExchangeRates = exchangeRatesRepository.getLiveExchangeRates()
        if (listOfSupportedCurrencies.isSuccessful && liveExchangeRates.isSuccessful) {
            listOfSupportedCurrencies.body()?.let { supportedCurrencies ->
                liveExchangeRates.body()?.let { exchangeRates ->
                    persistDataToDatabase(supportedCurrencies, exchangeRates)
                }
            }
        } else {
            // Todo handle errors
        }
        _progress.postValue(false)
    }

    private suspend fun persistDataToDatabase(
        supportedCurrencies: SupportedCurrenciesResponse,
        liveExchangeRates: LiveExchangeRatesResponse
    ) {
        val exchangeRatesList: MutableList<ExchangeRates> = mutableListOf()
        val currencies = supportedCurrencies.currencies
        val exchangeRatesQuotes = liveExchangeRates.quotes
        val exchangeRatesSource = liveExchangeRates.source

        for (currency in currencies) {
            val exchangeRate = ExchangeRates(
                currency.key,
                currency.value,
                exchangeRatesQuotes.getOrDefault("${exchangeRatesSource}${currency.key}", 0.0)
            )
            exchangeRatesList.add(exchangeRate)
        }
        exchangeRatesRepository.upsertExchangeRates(exchangeRatesList)
    }


}