package com.michaelflisar.composepreferences.screen.number

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.michaelflisar.composedialogs.core.DialogState
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.number.DialogNumberPicker
import com.michaelflisar.composedialogs.dialogs.number.NumberPickerSetup
import com.michaelflisar.composedialogs.dialogs.number.RepeatingButton
import com.michaelflisar.composedialogs.dialogs.number.rememberDialogNumber
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.BasePreferenceDialog
import com.michaelflisar.composepreferences.core.composables.PreferenceContentText
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetupDefaults
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle
import kotlin.math.roundToInt
import kotlin.math.roundToLong

/* --8<-- [start: constructor] */
/**
 * A number preference item - this item provides a number **picker** dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the [MutableState] of this item
 * @param min the minimum valid number
 * @param max the maximum valid number
 * @param stepSize the steps in which a number can be picked
 * @param formatter a formatter for a number
 */
@Composable
fun <T : Number> PreferenceScope.PreferenceNumber(
    style: PreferenceNumber.Style = PreferenceNumber.Style.Picker,
    // Special
    value: MutableState<T>,
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
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceNumberDefaults.dialog(
            dialogState,
            value.value,
            { value.value = it },
            min,
            max,
            stepSize,
            formatter,
            title,
            icon
        )
    }
)
        /* --8<-- [end: constructor] */ {
    PreferenceNumber(
        style = style,
        value = value.value,
        onValueChange = { value.value = it },
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
        titleRenderer = titleRenderer,
        subtitleRenderer = subtitleRenderer,
        filterTags = filterTags,
        dialog = dialog
    )
}

/* --8<-- [start: constructor2] */
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
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceNumberDefaults.dialog(
            dialogState,
            value,
            onValueChange,
            min,
            max,
            stepSize,
            formatter,
            title,
            icon
        )
    }
)
        /* --8<-- [end: constructor2] */ {
    when (style) {
        PreferenceNumber.Style.Picker -> {
            BasePreferenceDialog(
                dialogState = rememberDialogState(),
                dialog = dialog,
                itemSetup = itemSetup,
                enabled = enabled,
                visible = visible,
                title = title,
                subtitle = subtitle,
                icon = icon,
                itemStyle = itemStyle,
                titleRenderer = titleRenderer,
                subtitleRenderer = subtitleRenderer,
                filterTags = filterTags
            ) {
                PreferenceContentText(text = formatter(value), itemSetup)
            }
        }

        is PreferenceNumber.Style.Slider -> {
            BasePreference(
                itemSetup = itemSetup,
                enabled = enabled,
                visible = visible,
                title = title,
                subtitle = subtitle,
                icon = icon,
                itemStyle = itemStyle,
                titleRenderer = titleRenderer,
                subtitleRenderer = subtitleRenderer,
                filterTags = filterTags
            ) {
                ContentSlider(enabled, style, value, onValueChange, min, max, stepSize, formatter)
            }
        }

        is PreferenceNumber.Style.Buttons -> {
            BasePreference(
                itemSetup = itemSetup,
                enabled = enabled,
                visible = visible,
                title = title,
                subtitle = subtitle,
                icon = icon,
                itemStyle = itemStyle,
                titleRenderer = titleRenderer,
                subtitleRenderer = subtitleRenderer,
                filterTags = filterTags
            ) {
                ContentButtons(enabled, style, value, onValueChange, min, max, stepSize, formatter)
            }
        }
    }
}

@Composable
private fun <T : Number> ColumnScope.ContentSlider(
    enabled: Dependency,
    style: PreferenceNumber.Style.Slider,
    value: T,
    onValueChange: (T) -> Unit,
    min: T,
    max: T,
    stepSize: T,
    formatter: (T) -> String
) {
    val stateEnabled = enabled.state()
    val colors = if (style.showTicks) {
        SliderDefaults.colors()
    } else {
        SliderDefaults.colors(
            activeTickColor = Color.Transparent,
            inactiveTickColor = Color.Transparent
        )
    }

    if (style.showValueBelow) {
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toT(value)) },
            colors = colors,
            valueRange = min.toFloat()..max.toFloat(),
            steps = ((max.toFloat() - min.toFloat()) / stepSize.toFloat() - 1).toInt(),
            enabled = stateEnabled.value
        )
        Text(formatter(value), modifier = Modifier.align(Alignment.CenterHorizontally))
    } else {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Slider(
                modifier = Modifier.weight(1f),
                value = value.toFloat(),
                onValueChange = { onValueChange(it.toT(value)) },
                colors = colors,
                valueRange = min.toFloat()..max.toFloat(),
                steps = ((max.toFloat() - min.toFloat()) / stepSize.toFloat() - 1).toInt(),
                enabled = stateEnabled.value
            )
            Text(formatter(value))
        }
    }

}

@Composable
private fun <T : Number> ColumnScope.ContentButtons(
    enabled: Dependency,
    style: PreferenceNumber.Style.Buttons,
    value: T,
    onValueChange: (T) -> Unit,
    min: T,
    max: T,
    stepSize: T,
    formatter: (T) -> String
) {
    val stateEnabled = enabled.state()

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                val newValue = (value.toFloat() - stepSize.toFloat()).toT(value)
                    .takeIf { it.greaterThan(min, orEqual = true) } ?: min
                onValueChange(newValue)
            },
            enabled = stateEnabled.value && value.greaterThan(min, orEqual = false)
        ) {
            style.decrease()
        }
        Text(
            text = formatter(value),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        IconButton(
            onClick = {
                val newValue = (value.toFloat() + stepSize.toFloat()).toT(value)
                    .takeIf { it.smallerThan(max, orEqual = true) } ?: max
                onValueChange(newValue)
            },
            enabled = stateEnabled.value && value.smallerThan(max, orEqual = false)
        ) {
            style.increase()
        }
    }
}

@Suppress("UNCHECKED_CAST")
private fun <T : Number> Float.toT(value: T): T {
    return when (value) {
        is Int -> this.roundToInt() as T
        is Float -> this as T
        is Double -> this.toDouble() as T
        is Long -> this.roundToLong() as T
        else -> throw RuntimeException("Type ${this::class} not supported!")
    }
}

@Suppress("UNCHECKED_CAST")
private fun <T : Number> T.greaterThan(other: T, orEqual: Boolean): Boolean {
    return when (this) {
        is Int -> if (orEqual) this >= other as Int else this > other as Int
        is Float -> if (orEqual) this >= other as Float else this > other as Float
        is Double -> if (orEqual) this >= other as Double else this > other as Double
        is Long -> if (orEqual) this >= other as Long else this > other as Long
        else -> throw RuntimeException("Type ${this::class} not supported!")
    }
}

@Suppress("UNCHECKED_CAST")
private fun <T : Number> T.smallerThan(other: T, orEqual: Boolean): Boolean {
    return when (this) {
        is Int -> if (orEqual) this <= other as Int else this < other as Int
        is Float -> if (orEqual) this <= other as Float else this < other as Float
        is Double -> if (orEqual) this <= other as Double else this < other as Double
        is Long -> if (orEqual) this <= other as Long else this < other as Long
        else -> throw RuntimeException("Type ${this::class} not supported!")
    }
}

@Stable
object PreferenceNumber {
    sealed class Style {
        data object Picker : Style()
        class Slider(
            val showTicks: Boolean = false,
            val showValueBelow: Boolean = false
        ) : Style()
        class Buttons(
            val decrease: @Composable () -> Unit = {
                Icon(Icons.Default.Remove, null)
            },
            val increase: @Composable () -> Unit = {
                Icon(Icons.Default.Add, null)
            }
        ) : Style()
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
    fun itemSetupButtons() = PreferenceItemSetup(contentPlacementBottom = true)

    @Composable
    fun itemSetup(style: PreferenceNumber.Style) = when (style) {
        PreferenceNumber.Style.Picker -> itemSetupPicker()
        is PreferenceNumber.Style.Slider -> itemSetupSlider()
        is PreferenceNumber.Style.Buttons -> itemSetupButtons()
    }

    @Composable
    fun <T : Number> dialog(
        dialogState: DialogState,
        value: T,
        onValueChange: (value: T) -> Unit,
        min: T,
        max: T,
        stepSize: T,
        formatter: (value: T) -> String = { it.toString() },
        title: String,
        icon: (@Composable () -> Unit)? = null
    ) {
        val value = rememberDialogNumber(value)
        DialogNumberPicker(
            state = dialogState,
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
}