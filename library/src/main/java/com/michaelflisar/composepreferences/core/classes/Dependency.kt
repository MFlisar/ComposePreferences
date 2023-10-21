package com.michaelflisar.composepreferences.core.classes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

sealed class Dependency {

    @Composable
    abstract fun state(): androidx.compose.runtime.State<Boolean>

    data object Enabled : Dependency() {
        @Composable
        override fun state(): androidx.compose.runtime.State<Boolean> {
            return remember { mutableStateOf(true) }
        }
    }

    data object Disabled : Dependency() {
        @Composable
        override fun state(): androidx.compose.runtime.State<Boolean> {
            return remember { mutableStateOf(false) }
        }
    }

    class State<T>(
        private val state: androidx.compose.runtime.State<T>,
        val enabled: (T) -> Boolean
    ) : Dependency() {

        @Composable
        override fun state(): androidx.compose.runtime.State<Boolean> {
            return remember(state.value) {
                derivedStateOf {
                    enabled(state.value)
                }
            }
        }

    }
}

@Composable
fun <T> MutableState<T>.asDependency(enabled: (T) -> Boolean): Dependency.State<T> {
    return Dependency.State(this, enabled)
}