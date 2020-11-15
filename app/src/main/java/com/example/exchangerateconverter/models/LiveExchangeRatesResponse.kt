package com.example.exchangerateconverter.models

import com.google.gson.annotations.SerializedName

data class LiveExchangeRatesResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("quotes")
    val quotes: Map<String, Double>,
    @SerializedName("source")
    val source: String
)