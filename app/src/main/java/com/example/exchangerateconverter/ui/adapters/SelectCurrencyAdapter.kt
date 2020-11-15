package com.example.exchangerateconverter.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerateconverter.R
import com.example.exchangerateconverter.models.ExchangeRates
import kotlinx.android.synthetic.main.item_select_currency.view.*
import java.util.*
import kotlin.collections.ArrayList

class SelectCurrencyAdapter : RecyclerView.Adapter<SelectCurrencyAdapter.ViewHolder>(), Filterable {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var listOfExchangeRates = ArrayList<ExchangeRates>()
    private var listOfExchangeRatesAll = ArrayList<ExchangeRates>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_select_currency,
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

            item_flag_icon_image.setImageResource(
                context.resources.getIdentifier(
                    "drawable/_${exchangeRate.countryCode.toLowerCase(Locale.getDefault())}",
                    null, context.packageName
                )
            )

            setOnClickListener {
                onItemClickListener?.let { it(exchangeRate) }
            }
        }
    }

    fun setList(updatedListOfExchangeRates: List<ExchangeRates>) {
        listOfExchangeRates.clear()
        listOfExchangeRates.addAll(updatedListOfExchangeRates)
        listOfExchangeRatesAll = ArrayList(listOfExchangeRates)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val filteredList = ArrayList<ExchangeRates>()
                if (charSequence.toString().isEmpty()) {
                    filteredList.addAll(listOfExchangeRatesAll)
                } else {
                    for (exchangeRate in listOfExchangeRatesAll) {
                        if (exchangeRate.countryName.toLowerCase(Locale.getDefault()).contains(
                                charSequence.toString().toLowerCase(Locale.getDefault())
                            ) ||
                            exchangeRate.countryCode.toLowerCase(Locale.getDefault())
                                .contains(charSequence.toString().toLowerCase(Locale.getDefault()))
                        ) {
                            filteredList.add(exchangeRate)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults?
            ) {
                listOfExchangeRates.clear()
                listOfExchangeRates.addAll(filterResults?.values as Collection<ExchangeRates>)
                notifyDataSetChanged()
            }
        }
    }

    private var onItemClickListener: ((ExchangeRates) -> Unit)? = null

    fun setOnItemClickListener(listener: (ExchangeRates) -> Unit) {
        onItemClickListener = listener
    }

}