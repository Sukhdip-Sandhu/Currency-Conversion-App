package com.example.exchangerateconverter.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerateconverter.R
import com.example.exchangerateconverter.models.ExchangeRates
import kotlinx.android.synthetic.main.item_exchange_rate.view.*
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

class ExchangeRatesAdapter : RecyclerView.Adapter<ExchangeRatesAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var listOfExchangeRates = ArrayList<ExchangeRates>()

    private var userInputValue: Double = 0.0
    private var selectedCurrency: Double = 1.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_exchange_rate,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfExchangeRates.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exchangeRate = listOfExchangeRates[position]

        holder.itemView.apply {
            item_country_code_tv.text = exchangeRate.countryCode
            item_country_name_tv.text = exchangeRate.countryName

            val amount: Double = (userInputValue * exchangeRate.exchangeRate / selectedCurrency)
            val formatter = DecimalFormat("#,###.00")
            if (amount != 0.0) {
                item_exchange_rate_tv.text = formatter.format(amount)
            } else {
                item_exchange_rate_tv.text = "0"
            }

            item_flag_icon_image.setImageResource(
                context.resources.getIdentifier(
                    "drawable/_${exchangeRate.countryCode.toLowerCase(Locale.getDefault())}",
                    null, context.packageName
                )
            )

        }

    }

    fun setList(updatedListOfExchangeRates: List<ExchangeRates>) {
        listOfExchangeRates.clear()
        listOfExchangeRates.addAll(updatedListOfExchangeRates)
    }

    fun updateUserInputValue(newUserInputValue: Double?) {
        userInputValue = newUserInputValue!!
        notifyDataSetChanged()
    }

    fun updateUserSelectedCurrency(newUserSelectedCurrency: Double?) {
        selectedCurrency = newUserSelectedCurrency!!
        notifyDataSetChanged()
    }

}