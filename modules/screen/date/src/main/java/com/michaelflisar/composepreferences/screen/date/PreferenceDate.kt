package com.michaelflisar.composepreferences.screen.date

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.datetime.DialogDate
import com.michaelflisar.composedialogs.dialogs.datetime.DialogDateDefaults
import com.michaelflisar.composedialogs.dialogs.datetime.rememberDialogDate
import com.michaelflisar.composepreferences.core.classes.PreferenceData
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceContentText
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetupDefaults
import com.michaelflisar.composepreferences.core.composables.PreviewPreference
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * A date preference item - this item provides a date dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param data the [PreferenceData] of this item
 * @param firstDayOfWeek the first day of the week for the underlying date dialog
 * @param formatter the formatter for the selected date
 */
@Composable
fun PreferenceScope.PreferenceDate(
    // Special
    data: PreferenceData<LocalDate>,
    firstDayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    formatter: (date: LocalDate) -> String = {
        it.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
    },
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle
) {
    PreferenceDate(
        value = data.value,
        onValueChange = data.onValueChange,
        firstDayOfWeek = firstDayOfWeek,
        formatter = formatter,
        title = title,
        enabled = enabled,
        visible = visible,
        subtitle = subtitle,
        icon = icon,
        preferenceStyle = preferenceStyle
    )
}

/**
 * A date preference item - this item provides a date dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the value of this item
 * @param onValueChange the value changed callback of this item
 * @param firstDayOfWeek the first day of the week for the underlying date dialog
 * @param formatter the formatter for the selected date
 */
@Composable
fun PreferenceScope.PreferenceDate(
    // Special
    value: LocalDate,
    onValueChange: (date: LocalDate) -> Unit,
    firstDayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    formatter: (date: LocalDate) -> String = {
        it.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
    },
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle
) {
    val showDialog = rememberDialogState()
    if (showDialog.showing) {
        val value = rememberDialogDate(value)
        val setup = DialogDateDefaults.setup(
            formatterSelectedDate = formatter,
            firstDayOfWeek = firstDayOfWeek
        )
        DialogDate(
            state = showDialog,
            date = value,
            setup = setup,
            title = title,
            icon = icon
        ) {
            if (it.isPositiveButton) {
                onValueChange(value.value)
            }
        }
    }
    BasePreference(
        setup = PreferenceItemSetup(
          trailingContentSize = PreferenceItemSetupDefaults.datetime()
        ),
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
        PreferenceContentText(text = formatter(value))
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun Preview() {
    PreviewPreference {
        PreferenceDate(
            value = LocalDate.now(),
            onValueChange = {},
            icon = { Icon(Icons.Default.DateRange, null) },
            title = { Text(text = "Date Preference") },
            subtitle = { Text(text = "Click to open a date selector dialog") },
        )
    }
}