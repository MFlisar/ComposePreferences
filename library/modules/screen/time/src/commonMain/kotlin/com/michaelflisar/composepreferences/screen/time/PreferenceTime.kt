package com.michaelflisar.composepreferences.screen.time

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.time.DialogTime
import com.michaelflisar.composedialogs.dialogs.time.DialogTimeDefaults
import com.michaelflisar.composedialogs.dialogs.time.is24HourFormat
import com.michaelflisar.composedialogs.dialogs.time.rememberDialogTime
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceData
import com.michaelflisar.composepreferences.core.classes.PreferenceType
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceContentText
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetupDefaults
import com.michaelflisar.composepreferences.core.helper.SearchText
import com.michaelflisar.composepreferences.core.internal.rememberPreferenceItemState
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.char

/**
 * A color preference item - this item provides a time picker dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param data the [PreferenceData] of this item
 * @param is24Hours if true, the time picker shows a picker in 24h mode, otherwise it will use the 12h mode
 * @param formatter the formatter to format the time
 */
@Composable
fun PreferenceScope.PreferenceTime(
    // Special
    data: PreferenceData<LocalTime>,
    is24Hours: Boolean = is24HourFormat(), // comes from ComposeDialog
    formatter: (time: LocalTime) -> String = getDefaultTimeFormatter(is24Hours),
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceTimeDefaults.itemSetup(),
    filterTags: List<String> = emptyList()
) {
    PreferenceTime(
        value = data.value,
        onValueChange = data.onValueChange,
        is24Hours = is24Hours,
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
 * A color preference item - this item provides a time picker dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the value of this item
 * @param onValueChange the value changed callback of this item
 * @param is24Hours if true, the time picker shows a picker in 24h mode, otherwise it will use the 12h mode
 * @param formatter the formatter to format the time
 */
@Composable
fun PreferenceScope.PreferenceTime(
    // Special
    value: LocalTime,
    onValueChange: (value: LocalTime) -> Unit,
    is24Hours: Boolean = is24HourFormat(), // comes from ComposeDialog
    formatter: (time: LocalTime) -> String = getDefaultTimeFormatter(is24Hours),
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceTimeDefaults.itemSetup(),
    filterTags: List<String> = emptyList()
) {
    val showDialog = rememberDialogState()
    if (showDialog.showing) {
        val value = rememberDialogTime(value)
        val setup = DialogTimeDefaults.setup(
            is24Hours = is24Hours
        )
        DialogTime(
            state = showDialog,
            time = value,
            setup = setup,
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

@Stable
object PreferenceTimeDefaults {
    @Composable
    fun itemSetup() = PreferenceItemSetup(
        trailingContentSize = PreferenceItemSetupDefaults.datetime()
    )
}

private val TIME_FORMAT_24H = LocalTime.Format {
    hour()
    char(':')
    minute()
}

private val TIME_FORMAT_12H = LocalTime.Format {
    amPmHour()
    char(':')
    minute()
    char(' ')
    amPmMarker("AM", "PM")
}

private fun getDefaultTimeFormatter(is24Hours: Boolean): (time: LocalTime) -> String {
    return {
        val formatter = if (is24Hours) TIME_FORMAT_24H else TIME_FORMAT_12H
        formatter.format(it)
    }
}