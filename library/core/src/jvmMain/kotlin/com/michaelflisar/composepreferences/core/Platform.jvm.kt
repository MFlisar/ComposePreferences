package com.michaelflisar.composepreferences.core

import androidx.compose.runtime.Composable
import java.util.concurrent.atomic.AtomicInteger

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    // empty
}

actual typealias AtomicInt = AtomicInteger