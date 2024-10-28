package com.michaelflisar.composepreferences.core.classes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.michaelflisar.composepreferences.core.composables.BasePreferenceContainer
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup

/**
 * a simple class to define dependencies (boolean deduction from another state)
 */
sealed class Dependency {

    @Composable
    abstract fun state(): androidx.compose.runtime.State<Boolean>

    /**
     * a dependency that always evaluates to **true**
     *
     * @return a [Dependency] holding the dependency logic
     */
    data object Enabled : Dependency() {
        @Composable
        override fun state(): androidx.compose.runtime.State<Boolean> {
            return remember { mutableStateOf(true) }
        }
    }

    /**
     * a dependency that always evaluates to **false**
     *
     * @return a [Dependency] holding the dependency logic
     */
    data object Disabled : Dependency() {
        @Composable
        override fun state(): androidx.compose.runtime.State<Boolean> {
            return remember { mutableStateOf(false) }
        }
    }

    /**
     * a dependency that derives its state from a [androidx.compose.runtime.State] based on a provided deduction function
     *
     * @param state the state that this dependency depends on
     * @param enabled the deduction function that evaluates the deducted state of this dependency
     *
     * @return a [Dependency] holding the dependency logic
     */
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

/**
 * a convenient function to convert a [androidx.compose.runtime.State] into a [Dependency]
 *
 * @param enabled the deduction function that evaluates the deducted state of this dependency
 *
 * @return a [Dependency] holding the dependency logic
 */
@Composable
fun <T> MutableState<T>.asDependency(enabled: (T) -> Boolean): Dependency.State<T> {
    return Dependency.State(this, enabled)
}