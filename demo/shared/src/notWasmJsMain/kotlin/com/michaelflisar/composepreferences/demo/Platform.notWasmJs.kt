package com.michaelflisar.composepreferences.demo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual val DispatcherIO = Dispatchers.IO

actual typealias DemoDataStore = com.michaelflisar.composepreferences.demo.preferences.DemoDataStore