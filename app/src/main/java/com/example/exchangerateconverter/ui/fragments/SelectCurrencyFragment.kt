package com.example.exchangerateconverter.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.exchangerateconverter.R
import com.example.exchangerateconverter.ui.ExchangeRatesViewModel
import com.example.exchangerateconverter.ui.MainActivity
import com.example.exchangerateconverter.ui.adapters.SelectCurrencyAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_select_currency.*

@AndroidEntryPoint
class SelectCurrencyFragment : Fragment(R.layout.fragment_select_currency) {

    private val viewModel: ExchangeRatesViewModel by viewModels()
    private lateinit var selectCurrencyAdapter: SelectCurrencyAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        setupRecyclerView()

        selectCurrencyAdapter.setOnItemClickListener { exchangeRates ->
            val bundle = Bundle().apply {
                putSerializable("selectedExchangeRate", exchangeRates)
            }
            findNavController().navigate(
                R.id.action_selectCurrencyFragment_to_exchangeRateConverterFragment,
                bundle
            )
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_currency_menu, menu)
        val menuItem = menu.findItem(R.id.search)
        if (menuItem != null) {
            val searchView = menuItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        searchView.clearFocus()
                        selectCurrencyAdapter.filter.filter(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        selectCurrencyAdapter.filter.filter(newText)
                    }
                    return true
                }
            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupRecyclerView() {
        selectCurrencyAdapter = SelectCurrencyAdapter()
        select_currency_rv.apply {
            adapter = selectCurrencyAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
        displayCurrencyList()
    }

    private fun displayCurrencyList() {
        viewModel.getAllExchangeRates().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            selectCurrencyAdapter.setList(it)
            selectCurrencyAdapter.notifyDataSetChanged()
        })
    }

}