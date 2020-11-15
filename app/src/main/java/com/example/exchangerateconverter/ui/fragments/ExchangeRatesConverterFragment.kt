package com.example.exchangerateconverter.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.exchangerateconverter.R
import com.example.exchangerateconverter.ui.ExchangeRatesViewModel
import com.example.exchangerateconverter.ui.MainActivity
import com.example.exchangerateconverter.ui.adapters.ExchangeRatesAdapter
import com.example.exchangerateconverter.util.InputValidationUtil
import kotlinx.android.synthetic.main.fragment_exchange_rate_converter.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*

class ExchangeRatesConverterFragment : Fragment(R.layout.fragment_exchange_rate_converter) {

    private lateinit var viewModel: ExchangeRatesViewModel
    private lateinit var exchangeRatesAdapter: ExchangeRatesAdapter
    private val args: ExchangeRatesConverterFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setHasOptionsMenu(true)
        setupRecyclerView()

        country_code_tv.setOnClickListener {
            findNavController().navigate(
                R.id.action_exchangeRateConverterFragment_to_selectCurrencyFragment
            )
        }

        input_currency_edit_text.addTextChangedListener { input ->
            MainScope().launch {
                input?.let {
                    viewModel.updateUserCurrency(InputValidationUtil.handleInput(input.toString()))
                }
            }
        }

        viewModel.getUserCurrencyInput()
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer { userInputValue ->
                exchangeRatesAdapter.updateUserInputValue(userInputValue)
            })

        viewModel.getProgress().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            showProgressBar(it)
        })


        args.selectedExchangeRate?.let { selectedExchangeRate ->
            exchangeRatesAdapter.updateUserSelectedCurrency(selectedExchangeRate.exchangeRate)

            country_code_tv.text = selectedExchangeRate.countryCode

            input_currency_edit_text.setText(viewModel.getUserCurrencyInput().value?.let {
                viewModel.getUserCurrencyInput().value.toString()
            })

            // retrieves selected image and replaces imageview
            context?.resources?.getIdentifier(
                "drawable/_${selectedExchangeRate.countryCode.toLowerCase(Locale.getDefault())}",
                null,
                requireContext().packageName
            )?.let {
                flag_icon.setImageResource(it)
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.exchange_rate_menu, menu)
        menu.findItem(R.id.refresh)?.setOnMenuItemClickListener {
            viewModel.refreshData()
            true
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupRecyclerView() {
        exchangeRatesAdapter = ExchangeRatesAdapter()
        exchange_rates_rv.apply {
            adapter = exchangeRatesAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
        displayExchangeRates()
    }

    private fun displayExchangeRates() {
        viewModel.getAllExchangeRates().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            exchangeRatesAdapter.setList(it)
            exchangeRatesAdapter.notifyDataSetChanged()
        })
    }

    private fun showProgressBar(showProgressBar: Boolean) {
        if (showProgressBar) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.INVISIBLE
        }
    }

}