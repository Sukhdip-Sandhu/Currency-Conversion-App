package com.example.exchangerateconverter.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val KEY_LAST_FETCH_TIME = "key_last_fetch_time"

class PreferenceProvider(
    context: Context
) {

    private val appContext = context.applicationContext

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun saveLatestFetchTime(fetchedAt: Int) {
        preference.edit().putInt(KEY_LAST_FETCH_TIME, fetchedAt).apply()
    }

    fun getLastFetchTime(): Int {
        return preference.getInt(KEY_LAST_FETCH_TIME, 0)
    }

}