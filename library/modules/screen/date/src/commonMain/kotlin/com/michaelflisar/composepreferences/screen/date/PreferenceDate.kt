package com.michaelflisar.composepreferences.screen.date

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.date.DialogDate
import com.michaelflisar.composedialogs.dialogs.date.DialogDateDefaults
import com.michaelflisar.composedialogs.dialogs.date.defaultFormatterSelectedDate
import com.michaelflisar.composedialogs.dialogs.date.rememberDialogDate
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
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

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
        // comes from the ComposeDialog library
        defaultFormatterSelectedDate(it)
    },
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceDateDefaults.itemSetup(),
    filterTags: List<String> = emptyList()
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
        itemStyle = itemStyle,
        itemSetup = itemSetup,
        filterTags = filterTags
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
        // comes from the ComposeDialog library
        defaultFormatterSelectedDate(it)
    },
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceDateDefaults.itemSetup(),
    filterTags: List<String> = emptyList()
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
object PreferenceDateDefaults {
    @Composable
    fun itemSetup() = PreferenceItemSetup(
        trailingContentSize = PreferenceItemSetupDefaults.datetime()
    )
}