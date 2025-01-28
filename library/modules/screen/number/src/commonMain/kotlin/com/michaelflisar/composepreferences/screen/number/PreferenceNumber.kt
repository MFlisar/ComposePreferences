package com.michaelflisar.composepreferences.screen.number

import androidx.compose.foundation.layout.ColumnScope
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

    Slider(
        value = value.toFloat(),
        onValueChange = { onValueChange(toT(value, it)) },
        colors = colors,
        valueRange = min.toFloat()..max.toFloat(),
        steps = ((max.toFloat() - min.toFloat()) / stepSize.toFloat() - 1).toInt(),
        enabled = stateEnabled.value
    )
    Text(formatter(value), modifier = Modifier.align(Alignment.CenterHorizontally))
}

@Suppress("UNCHECKED_CAST")
private fun <T : Number> toT(value: T, v: Float): T {
    return when (value) {
        is Int -> v.roundToInt() as T
        is Float -> v as T
        is Double -> v.toDouble() as T
        is Long -> v.roundToLong() as T
        else -> throw RuntimeException("Type ${value::class} not supported!")
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