package com.example.exchangerateconverter.di

import android.content.Context
import androidx.room.Room
import com.example.exchangerateconverter.db.ExchangeRatesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context.applicationContext,
        ExchangeRatesDatabase::class.java,
        "exchange_rate.db"
    ).build()

    @Singleton
    @Provides
    fun provideDao(
        database: ExchangeRatesDatabase
    ) = database.getExchangeRateDao()

}