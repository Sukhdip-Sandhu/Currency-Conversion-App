package com.example.exchangerateconverter.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "exchange_rates_table"
)
data class ExchangeRates(
    @PrimaryKey
    @SerializedName("countryCode")
    var countryCode: String = "",

    @SerializedName("countryName")
    var countryName: String = "",

    @SerializedName("exchangeRate")
    var exchangeRate: Double = 0.0
) : Serializable