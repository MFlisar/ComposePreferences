package com.michaelflisar.composepreferences.screen.bool

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceData
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetupDefaults
import com.michaelflisar.composepreferences.core.composables.PreviewPreference
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

@Composable
fun PreferenceScope.PreferenceBool(
    style: PreferenceBool.Style = PreferenceBool.Style.Switch,
    // Special
    data: PreferenceData<Boolean>,
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle
) {
    PreferenceBool(
        style = style,
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
fun PreferenceScope.PreferenceBool(
    style: PreferenceBool.Style = PreferenceBool.Style.Switch,
    // Special
    value: Boolean,
    onValueChange: (selected: Boolean) -> Unit,
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle
) {
    var checked by remember(value) { mutableStateOf(value) }
    val onClick = if (LocalPreferenceSettings.current.toggleBooleanOnItemClick) {
        {
            val updated = !checked
            onValueChange(updated)
            checked = updated
        }
    } else null

    // Switch is larger than Checkbox and has 52x32 DP
    BasePreference(
        setup = PreferenceItemSetup(
            trailingContentSize = PreferenceItemSetupDefaults.trailingContentSize(52.dp)
        ),
        enabled = enabled,
        visible = visible,
        title = title,
        subtitle = subtitle,
        icon = icon,
        preferenceStyle = preferenceStyle,
        onClick = onClick
    ) {
        when (style) {
            PreferenceBool.Style.Checkbox -> {
                Checkbox(checked = checked, onCheckedChange = {
                    onValueChange(it)
                    checked = it
                })
            }

            PreferenceBool.Style.Switch -> {
                Switch(checked = checked, onCheckedChange = {
                    onValueChange(it)
                    checked = it
                })
            }
        }
    }
}

@Stable
object PreferenceBool {
    enum class Style {
        Checkbox,
        Switch
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun Preview() {
    PreviewPreference {
        PreferenceBool(
            style = PreferenceBool.Style.Checkbox,
            value = true,
            onValueChange = {},
            icon = { Icon(Icons.Default.Info, null) },
            title = { Text(text = "Checkbox Title") },
            subtitle = { Text(text = "This is a description") },
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun Preview2() {
    PreviewPreference {
        PreferenceBool(
            style = PreferenceBool.Style.Switch,
            value = true,
            onValueChange = {},
            icon = { Icon(Icons.Default.Info, null) },
            title = { Text(text = "Switch Title") },
            subtitle = { Text(text = "This is a description") },
        )
    }
}