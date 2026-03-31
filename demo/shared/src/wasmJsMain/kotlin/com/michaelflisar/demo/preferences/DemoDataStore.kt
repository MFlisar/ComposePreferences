package com.michaelflisar.demo.preferences

import com.michaelflisar.demo.interfaces.IDemoDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

// SIMPLE demo class only... just for saving data inside memory
class DemoDataStore : IDemoDataStore {

    private val state = MutableStateFlow<Map<String, Any?>>(emptyMap())

    // ----------
    // String
    // ----------

    override fun getString(key: String, defaultValue: String) =
        state.map { it[key] as? String ?: defaultValue }

    override suspend fun update(key: String, value: String) {
        state.update { it + (key to value) }
    }

    // ----------
    // Bool
    // ----------

    override fun getBool(key: String, defaultValue: Boolean) =
        state.map { it[key] as? Boolean ?: defaultValue }

    override suspend fun update(key: String, value: Boolean) {
        state.update { it + (key to value) }
    }

    // ----------
    // Int
    // ----------

    override fun getInt(key: String, defaultValue: Int) =
        state.map { it[key] as? Int ?: defaultValue }

    override suspend fun update(key: String, value: Int) {
        state.update { it + (key to value) }
    }

    // ----------
    // Long
    // ----------

    override fun getLong(key: String, defaultValue: Long) =
        state.map { it[key] as? Long ?: defaultValue }

    override suspend fun update(key: String, value: Long) {
        state.update { it + (key to value) }
    }

    // ----------
    // Float
    // ----------

    override fun getFloat(key: String, defaultValue: Float) =
        state.map { it[key] as? Float ?: defaultValue }

    override suspend fun update(key: String, value: Float) {
        state.update { it + (key to value) }
    }

    // ----------
    // IntList
    // ----------

    override fun getIntList(key: String, defaultValue: List<Int>) =
        state.map {
            @Suppress("UNCHECKED_CAST")
            it[key] as? List<Int> ?: defaultValue
        }

    override suspend fun update(key: String, value: List<Int>) {
        state.update { it + (key to value) }
    }
}
