package com.michaelflisar.composepreferences.demo

import com.michaelflisar.composepreferences.demo.interfaces.IDemoDataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map

expect val DispatcherIO: CoroutineDispatcher

expect class DemoDataStore : IDemoDataStore