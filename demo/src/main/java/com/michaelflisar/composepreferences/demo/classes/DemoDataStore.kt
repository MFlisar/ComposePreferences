package com.michaelflisar.composepreferences.demo.classes

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val LocalDataStore = compositionLocalOf { DemoDataStore() }

// SIMPLE demo class only... just for saving data inside a preference file
class DemoDataStore(val context: Context? = null) {

    companion object {
        private val Context.dataStore by preferencesDataStore("data")
    }

    private val requireContext: Context
        get() = context!!

    private val flow = requireContext.dataStore.data

    suspend fun preload() {
        requireContext.dataStore.data.first()
    }

    // ----------
    // String
    // ----------

    fun getString(key: String, defaultValue: String = "") =
        flow.map { it[stringPreferencesKey(key)] ?: defaultValue }

    suspend fun update(key: String, value: String) {
        requireContext.dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    // ----------
    // Bool
    // ----------

    fun getBool(key: String, defaultValue: Boolean = true) =
        flow.map { it[booleanPreferencesKey(key)] ?: defaultValue }

    suspend fun update(key: String, value: Boolean) {
        requireContext.dataStore.edit {
            it[booleanPreferencesKey(key)] = value
        }
    }

    // ----------
    // Int
    // ----------

    fun getInt(key: String, defaultValue: Int = 0) =
        flow.map { it[intPreferencesKey(key)] ?: defaultValue }

    suspend fun update(key: String, value: Int) {
        requireContext.dataStore.edit {
            it[intPreferencesKey(key)] = value
        }
    }

    // ----------
    // Long
    // ----------

    fun getLong(key: String, defaultValue: Long = 0L) =
        flow.map { it[longPreferencesKey(key)] ?: defaultValue }

    suspend fun update(key: String, value: Long) {
        requireContext.dataStore.edit {
            it[longPreferencesKey(key)] = value
        }
    }

    // ----------
    // Float
    // ----------

    fun getFloat(key: String, defaultValue: Float = 0f) =
        flow.map { it[floatPreferencesKey(key)] ?: defaultValue }

    suspend fun update(key: String, value: Float) {
        requireContext.dataStore.edit {
            it[floatPreferencesKey(key)] = value
        }
    }

    // ----------
    // Int
    // ----------

    fun getIntList(key: String, defaultValue: List<Int> = emptyList()) =
        flow.map {
            val data = it[stringPreferencesKey(key)]
            if (data == null)
                defaultValue
            else if (data.isEmpty())
                emptyList()
            else
                data.split("|").map { it.toInt() }
        }

    suspend fun update(key: String, value: List<Int>) {
        requireContext.dataStore.edit {
            it[stringPreferencesKey(key)] =
                if (value.isEmpty()) "" else value.joinToString("|")
        }
    }
}