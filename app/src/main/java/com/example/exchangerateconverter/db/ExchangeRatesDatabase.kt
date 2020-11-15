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

    companion object {
        @Volatile
        private var INSTANCE: ExchangeRatesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: createDatabase(context).also { INSTANCE = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ExchangeRatesDatabase::class.java,
                "exchange_rate.db"
            ).build()
    }

}