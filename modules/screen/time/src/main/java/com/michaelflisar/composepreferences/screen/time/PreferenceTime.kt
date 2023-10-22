package com.michaelflisar.composepreferences.screen.time

import android.content.res.Configuration
import android.text.format.DateFormat
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.datetime.DialogTime
import com.michaelflisar.composedialogs.dialogs.datetime.DialogTimeDefaults
import com.michaelflisar.composedialogs.dialogs.datetime.rememberDialogTime
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
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun PreferenceScope.PreferenceTime(
    // Special
    data: PreferenceData<LocalTime>,
    is24Hours: Boolean = DateFormat.is24HourFormat(LocalContext.current),
    formatter: (time: LocalTime) -> String = getDefaultTimeFormatter(is24Hours),
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle
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
        preferenceStyle = preferenceStyle
    )
}

@Composable
fun PreferenceScope.PreferenceTime(
    // Special
    value: LocalTime,
    onValueChange: (value: LocalTime) -> Unit,
    is24Hours: Boolean = DateFormat.is24HourFormat(LocalContext.current),
    formatter: (time: LocalTime) -> String = getDefaultTimeFormatter(is24Hours),
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
        val value = rememberDialogTime(value)
        val setup = DialogTimeDefaults.setup(
            is24Hours = is24Hours
        )
        DialogTime(
            state = showDialog,
            time = value,
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

private fun getDefaultTimeFormatter(is24Hours: Boolean): (time: LocalTime) -> String {
    return {
        val pattern = if (is24Hours) {
            "HH:mm"
        } else "hh:mm a"
        DateTimeFormatter.ofPattern(pattern).format(it)
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun Preview() {
    PreviewPreference {
        PreferenceTime(
            value = LocalTime.now(),
            onValueChange = {},
            is24Hours = true,
            icon = { Icon(Icons.Default.Refresh, null) },
            title = { Text(text = "Time Title") },
            subtitle = { Text(text = "Select a time inside a time picker dialog") },
        )
    }
}
