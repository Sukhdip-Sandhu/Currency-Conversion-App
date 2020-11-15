package com.example.exchangerateconverter.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.exchangerateconverter.models.ExchangeRates
import com.example.exchangerateconverter.pref.PreferenceProvider
import com.example.exchangerateconverter.repository.ExchangeRatesRepository
import com.example.exchangerateconverter.util.Constants.Companion.FETCH_INTERVAL
import kotlinx.coroutines.launch
import java.lang.Exception

class ExchangeRatesViewModel(
    private val exchangeRatesRepository: ExchangeRatesRepository,
    application: Application
) : AndroidViewModel(application) {

    private val preferences = PreferenceProvider(context = getApplication())

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
        if (isFetchNeeded()) {
            _progress.postValue(true)
            try {
                exchangeRatesRepository.fetchAndCacheExchangeRates()
                val currentTime = System.currentTimeMillis() / 1000L
                preferences.saveLatestFetchTime(currentTime.toInt())
            } catch (e: Exception) {
                // Todo: Error handling
            }
            _progress.postValue(false)
        }
    }

    private fun isFetchNeeded(): Boolean {
        val lastFetchTime = preferences.getLastFetchTime()
        if (lastFetchTime == 0) { // first fetch
            return true
        }
        val currentTime = System.currentTimeMillis() / 1000L
        if (currentTime - lastFetchTime >= FETCH_INTERVAL) {
            return true
        }
        return false
    }

}