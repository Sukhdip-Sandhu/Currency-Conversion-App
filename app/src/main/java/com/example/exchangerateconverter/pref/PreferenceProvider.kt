package com.example.exchangerateconverter.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

private const val KEY_LAST_FETCH_TIME = "key_last_fetch_time"

@ActivityRetainedScoped
class PreferenceProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveLatestFetchTime(fetchedAt: Int) {
        preference.edit().putInt(KEY_LAST_FETCH_TIME, fetchedAt).apply()
    }

    fun getLastFetchTime(): Int {
        return preference.getInt(KEY_LAST_FETCH_TIME, 0)
    }

}