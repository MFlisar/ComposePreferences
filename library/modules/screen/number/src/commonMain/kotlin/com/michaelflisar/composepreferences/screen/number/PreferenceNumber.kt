package com.michaelflisar.composepreferences.screen.number

import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.number.DialogNumberPicker
import com.michaelflisar.composedialogs.dialogs.number.NumberPickerSetup
import com.michaelflisar.composedialogs.dialogs.number.RepeatingButton
import com.michaelflisar.composedialogs.dialogs.number.rememberDialogNumber
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceData
import com.michaelflisar.composepreferences.core.classes.PreferenceType
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceContentText
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetupDefaults
import com.michaelflisar.composepreferences.core.helper.SearchText
import com.michaelflisar.composepreferences.core.internal.rememberPreferenceItemState
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle

/**
 * A number preference item - this item provides a number **picker** dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param data the [PreferenceData] of this item
 * @param min the minimum valid number
 * @param max the maximum valid number
 * @param stepSize the steps in which a number can be picked
 * @param formatter a formatter for a number
 */
@Composable
fun <T : Number> PreferenceScope.PreferenceNumber(
    style: PreferenceNumber.Style = PreferenceNumber.Style.Picker,
    // Special
    data: PreferenceData<T>,
    min: T,
    max: T,
    stepSize: T,
    formatter: (value: T) -> String = { it.toString() },
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceNumberDefaults.itemSetup(style),
    filterTags: List<String> = emptyList()
) {
    PreferenceNumber(
        style = style,
        value = data.value,
        onValueChange = data.onValueChange,
        min = min,
        max = max,
        stepSize = stepSize,
        formatter = formatter,
        title = title,
        enabled = enabled,
        visible = visible,
        subtitle = subtitle,
        icon = icon,
        itemStyle = itemStyle,
        itemSetup = itemSetup,
        filterTags = filterTags
    )
}

/**
 * A number preference item - this item provides a number **picker** dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the value of this item
 * @param onValueChange the value changed callback of this item
 * @param min the minimum valid number
 * @param max the maximum valid number
 * @param stepSize the steps in which a number can be picked
 * @param formatter a formatter for a number
 */
@Composable
fun <T : Number> PreferenceScope.PreferenceNumber(
    style: PreferenceNumber.Style = PreferenceNumber.Style.Picker,
    // Special
    value: T,
    onValueChange: (value: T) -> Unit,
    min: T,
    max: T,
    stepSize: T,
    formatter: (value: T) -> String = { it.toString() },
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceNumberDefaults.itemSetup(style),
    filterTags: List<String> = emptyList()
) {
    when (style) {
        PreferenceNumber.Style.Picker -> {
            val showDialog = rememberDialogState()
            if (showDialog.showing) {
                val value = rememberDialogNumber(value)
                DialogNumberPicker(
                    state = showDialog,
                    value = value,
                    setup = NumberPickerSetup(
                        min, max, stepSize, repeatingButton = RepeatingButton.Enabled()
                    ),
                    formatter = formatter,
                    title = { Text(title) },
                    icon = icon
                ) {
                    if (it.isPositiveButton) {
                        onValueChange(value.value)
                    }
                }
            }

            BasePreference(
                itemSetup = itemSetup,
                enabled = enabled,
                visible = visible,
                title = title,
                subtitle = subtitle,
                icon = icon,
                itemStyle = itemStyle,
                filterTags = filterTags,
                onClick = {
                    showDialog.show()
                }
            ) {
                PreferenceContentText(text = formatter(value), itemSetup)
            }
        }

        is PreferenceNumber.Style.Slider -> {
            val colors = if (style.showTicks) {
                SliderDefaults.colors()
            } else {
                SliderDefaults.colors(
                    activeTickColor = Color.Transparent,
                    inactiveTickColor = Color.Transparent
                )
            }

            BasePreference(
                itemSetup = itemSetup,
                enabled = enabled,
                visible = visible,
                title = title,
                subtitle = subtitle,
                icon = icon,
                itemStyle = itemStyle,
                filterTags = filterTags
            ) {
                Slider(
                    value = value.toFloat(),
                    onValueChange = {
                        @Suppress("UNCHECKED_CAST")
                        when (value) {
                            is Int -> onValueChange(it.toInt() as T)
                            is Float -> onValueChange(it as T)
                            is Double -> onValueChange(it.toDouble() as T)
                            is Long -> onValueChange(it.toLong() as T)
                        }
                    },
                    colors = colors,
                    valueRange = min.toFloat()..max.toFloat(),
                    steps = ((max.toFloat() - min.toFloat()) / stepSize.toFloat() - 1).toInt()
                )
                Text(formatter(value), modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Stable
object PreferenceNumber {
    sealed class Style {
        data object Picker : Style()
        class Slider(val showTicks: Boolean = false) : Style()
    }
}

@Stable
object PreferenceNumberDefaults {
    @Composable
    fun itemSetupPicker() = PreferenceItemSetup(
        trailingContentSize = PreferenceItemSetupDefaults.numericContent()
    )

    @Composable
    fun itemSetupSlider() = PreferenceItemSetup(contentPlacementBottom = true)

    @Composable
    fun itemSetup(style: PreferenceNumber.Style) = when (style) {
        PreferenceNumber.Style.Picker -> itemSetupPicker()
        is PreferenceNumber.Style.Slider -> itemSetupSlider()
    }
}