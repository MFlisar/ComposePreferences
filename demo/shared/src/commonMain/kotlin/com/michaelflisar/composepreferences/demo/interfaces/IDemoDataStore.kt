package com.michaelflisar.composepreferences.demo.interfaces

import kotlinx.coroutines.flow.Flow

interface IDemoDataStore {

    fun getString(key: String, defaultValue: String = "") : Flow<String>

    suspend fun update(key: String, value: String)

    // ----------
    // Bool
    // ----------

    fun getBool(key: String, defaultValue: Boolean = true) : Flow<Boolean>

    suspend fun update(key: String, value: Boolean)

    // ----------
    // Int
    // ----------

    fun getInt(key: String, defaultValue: Int = 0): Flow<Int>

    suspend fun update(key: String, value: Int)

    // ----------
    // Long
    // ----------

    fun getLong(key: String, defaultValue: Long = 0L) : Flow<Long>

    suspend fun update(key: String, value: Long)

    // ----------
    // Float
    // ----------

    fun getFloat(key: String, defaultValue: Float = 0f) : Flow<Float>

    suspend fun update(key: String, value: Float)

    // ----------
    // Int
    // ----------

    fun getIntList(key: String, defaultValue: List<Int> = emptyList()): Flow<List<Int>>

    suspend fun update(key: String, value: List<Int>)
}