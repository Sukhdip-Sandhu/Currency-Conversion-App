package com.example.exchangerateconverter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.exchangerateconverter.R
import com.example.exchangerateconverter.ui.ExchangeRatesViewModel

class ExchangeRatesConverterFragment : Fragment(R.layout.fragment_exchange_rate_converter) {

    lateinit var viewModel : ExchangeRatesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}