package com.example.exchangerateconverter.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.exchangerateconverter.repository.ExchangeRatesRepository

class ExchangeRatesViewModelProvider(
    private val exchangeRatesRepository: ExchangeRatesRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExchangeRatesViewModel(exchangeRatesRepository, application) as T
    }
}