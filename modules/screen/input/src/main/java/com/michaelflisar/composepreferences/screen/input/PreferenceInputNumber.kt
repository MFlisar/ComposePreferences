package com.michaelflisar.composepreferences.screen.input

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.input.DialogInputNumber
import com.michaelflisar.composedialogs.dialogs.input.rememberDialogInputNumber
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

@Composable
fun <T : Number> PreferenceScope.PreferenceInputNumber(
    // Special
    data: PreferenceData<T>,
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle
) {
    PreferenceInputNumber(
        value = data.value,
        onValueChange = data.onValueChange,
        title = title,
        enabled = enabled,
        visible = visible,
        subtitle = subtitle,
        icon = icon,
        preferenceStyle = preferenceStyle
    )
}

@Composable
fun <T : Number> PreferenceScope.PreferenceInputNumber(
    // Special
    value: T,
    onValueChange: (value: T) -> Unit,
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
        val value = rememberDialogInputNumber(value)
        DialogInputNumber(
            state = showDialog,
            value = value,
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
            trailingContentSize = PreferenceItemSetupDefaults.numericContent()
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
        PreferenceContentText(value.toString())
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun Preview() {
    PreviewPreference {
        PreferenceInputNumber(
            value = 100,
            onValueChange = {},
            icon = { Icon(Icons.Default.Info, null) },
            title = { Text(text = "Input Number Title") },
            subtitle = { Text(text = "This is a description") },
        )
    }
}