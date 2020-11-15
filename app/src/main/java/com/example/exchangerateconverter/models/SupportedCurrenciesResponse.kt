package com.example.exchangerateconverter.models

import com.google.gson.annotations.SerializedName

class SupportedCurrenciesResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("currencies")
    val currencies: Map<String, String>
)