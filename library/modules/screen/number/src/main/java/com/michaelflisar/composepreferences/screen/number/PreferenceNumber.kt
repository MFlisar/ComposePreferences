package com.michaelflisar.composepreferences.screen.number

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.input.DialogNumberPicker
import com.michaelflisar.composedialogs.dialogs.input.NumberPickerSetup
import com.michaelflisar.composedialogs.dialogs.input.RepeatingButton
import com.michaelflisar.composedialogs.dialogs.input.rememberDialogNumber
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceData
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceContentText
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetupDefaults
import com.michaelflisar.composepreferences.core.composables.PreviewPreference
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

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
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle,
    itemSetup: PreferenceItemSetup = PreferenceNumberDefaults.itemSetup(style)
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
        preferenceStyle = preferenceStyle,
        itemSetup = itemSetup
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
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle,
    itemSetup: PreferenceItemSetup = PreferenceNumberDefaults.itemSetup(style)
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
                    title = title,
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
                preferenceStyle = preferenceStyle,
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
                preferenceStyle = preferenceStyle
            ) {
                Slider(
                    value = value.toFloat(),
                    onValueChange = {
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

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun Preview() {
    PreviewPreference {
        PreferenceNumber(
            value = 3,
            onValueChange = {},
            min = 0,
            max = 5,
            stepSize = 1,
            icon = { Icon(Icons.Default.Star, null) },
            title = { Text(text = "Input Number Title") },
            subtitle = { Text(text = "This allows you to PICK any valid INTEGER inside a dialog with a number picker") },
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun Preview2() {
    PreviewPreference {
        PreferenceNumber(
            style = PreferenceNumber.Style.Slider(),
            value = 3,
            onValueChange = {},
            min = 0,
            max = 5,
            stepSize = 1,
            icon = { Icon(Icons.Default.Star, null) },
            title = { Text(text = "Input Number Title") },
            subtitle = { Text(text = "Select a number with a slider") },
        )
    }
}
