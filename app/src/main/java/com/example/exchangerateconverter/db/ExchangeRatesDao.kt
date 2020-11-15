package com.example.exchangerateconverter.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.exchangerateconverter.models.ExchangeRates

@Dao
interface ExchangeRatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertExchangeRates(exchangeRates: List<ExchangeRates>)

    @Query("SELECT * FROM exchange_rates_table")
    fun getAllExchangeRates(): LiveData<List<ExchangeRates>>

}