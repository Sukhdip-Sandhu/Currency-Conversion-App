package com.example.exchangerateconverter.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.exchangerateconverter.models.ExchangeRates
import com.google.common.truth.Truth.assertThat
import getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ExchangeRatesDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ExchangeRatesDatabase
    private lateinit var dao: ExchangeRatesDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ExchangeRatesDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getExchangeRateDao()
    }

    @Test
    fun insertListOfExchangeRates() = runBlockingTest {
        val exchangeRate = ExchangeRates("ABC", "Country", 1.0)
        dao.upsertExchangeRates(listOf(exchangeRate))

        val allExchangeRates = dao.getAllExchangeRates().getOrAwaitValue()
        assertThat(allExchangeRates).contains(exchangeRate)
    }

    @Test
    fun updateListOfExchangeRatesShouldReplaceExistingList() = runBlockingTest {
        val exchangeRate = ExchangeRates("ABC", "Country", 1.0)
        dao.upsertExchangeRates(listOf(exchangeRate))

        // Change the value of the exchange rate and add it back into the database
        exchangeRate.exchangeRate = 1.0
        dao.upsertExchangeRates(listOf(exchangeRate))

        val allExchangeRates = dao.getAllExchangeRates().getOrAwaitValue()
        assertThat(allExchangeRates.size).isEqualTo(1)
    }

    @Test
    fun updateExchangeRatesListShouldUpdateValues() = runBlockingTest {
        val exchangeRate = ExchangeRates("ABC", "Country", 1.0)
        dao.upsertExchangeRates(listOf(exchangeRate))

        // Change the value of the exchange rate and add it back into the database
        exchangeRate.exchangeRate = 1.23
        dao.upsertExchangeRates(listOf(exchangeRate))

        val allExchangeRates = dao.getAllExchangeRates().getOrAwaitValue()
        assertThat(allExchangeRates[0].exchangeRate).isEqualTo(1.23)
    }

    @After
    fun teardown() {
        database.close()
    }

}