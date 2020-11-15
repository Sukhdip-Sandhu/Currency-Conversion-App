package com.example.exchangerateconverter.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.exchangerateconverter.repository.ExchangeRatesRepository

class ExchangeRatesViewModel(
    private val exchangeRatesRepository: ExchangeRatesRepository,
    application: Application
) : AndroidViewModel(application) {

}