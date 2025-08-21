package com.michaelflisar.composepreferences.demo.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.michaelflisar.composepreferences.demo.interfaces.IDemoDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// SIMPLE demo class only... just for saving data inside a preference file
class DemoDataStore(
    val dataStore: DataStore<Preferences>
) : IDemoDataStore {
    private val flow = dataStore.data

    suspend fun preload() {
        dataStore.data.first()
    }

    // ----------
    // String
    // ----------

    override fun getString(key: String, defaultValue: String) =
        flow.map { it[stringPreferencesKey(key)] ?: defaultValue }

    override suspend fun update(key: String, value: String) {
        dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    // ----------
    // Bool
    // ----------

    override fun getBool(key: String, defaultValue: Boolean) =
        flow.map { it[booleanPreferencesKey(key)] ?: defaultValue }

    override suspend fun update(key: String, value: Boolean) {
        dataStore.edit {
            it[booleanPreferencesKey(key)] = value
        }
    }

    // ----------
    // Int
    // ----------

    override fun getInt(key: String, defaultValue: Int) =
        flow.map { it[intPreferencesKey(key)] ?: defaultValue }

    override suspend fun update(key: String, value: Int) {
        dataStore.edit {
            it[intPreferencesKey(key)] = value
        }
    }

    // ----------
    // Long
    // ----------

    override fun getLong(key: String, defaultValue: Long) =
        flow.map { it[longPreferencesKey(key)] ?: defaultValue }

    override suspend fun update(key: String, value: Long) {
        dataStore.edit {
            it[longPreferencesKey(key)] = value
        }
    }

    // ----------
    // Float
    // ----------

    override fun getFloat(key: String, defaultValue: Float) =
        flow.map { it[floatPreferencesKey(key)] ?: defaultValue }

    override suspend fun update(key: String, value: Float) {
        dataStore.edit {
            it[floatPreferencesKey(key)] = value
        }
    }

    // ----------
    // Int
    // ----------

    override fun getIntList(key: String, defaultValue: List<Int>) =
        flow.map {
            val data = it[stringPreferencesKey(key)]
            if (data == null)
                defaultValue
            else if (data.isEmpty())
                emptyList()
            else
                data.split("|").map { it.toInt() }
        }

    override suspend fun update(key: String, value: List<Int>) {
        dataStore.edit {
            it[stringPreferencesKey(key)] =
                if (value.isEmpty()) "" else value.joinToString("|")
        }
    }
}