package com.example.exchangerateconverter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.exchangerateconverter.R
import com.example.exchangerateconverter.db.ExchangeRatesDatabase
import com.example.exchangerateconverter.repository.ExchangeRatesRepository

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ExchangeRatesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val exchangeRatesRepository = ExchangeRatesRepository(ExchangeRatesDatabase(this))

        val viewModelProviderFactory =
            ExchangeRatesViewModelProvider(exchangeRatesRepository, application)

        viewModel =
            ViewModelProvider(
                this,
                viewModelProviderFactory
            ).get(ExchangeRatesViewModel::class.java)

    }
}