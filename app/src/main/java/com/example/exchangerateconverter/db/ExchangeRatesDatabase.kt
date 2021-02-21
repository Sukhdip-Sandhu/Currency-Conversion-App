package com.example.exchangerateconverter.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.exchangerateconverter.models.ExchangeRates


@Database(
    entities = [ExchangeRates::class],
    version = 1
)
abstract class ExchangeRatesDatabase : RoomDatabase() {

    abstract fun getExchangeRateDao(): ExchangeRatesDao

}