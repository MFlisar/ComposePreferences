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

/**
 * A bool preference item - this item shows a checkbox/switch which reflects the preference state
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param style the [PreferenceBool.Style] of this item ([PreferenceBool.Style.Switch] or [PreferenceBool.Style.Checkbox])
 * @param data the [PreferenceData] of this item
 */
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
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle,
    itemSetup: PreferenceItemSetup = PreferenceBoolDefaults.itemSetup()
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
        preferenceStyle = preferenceStyle,
        itemSetup = itemSetup
    )
}

/**
 * A bool preference item - this item shows a checkbox/switch which reflects the preference state
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param style the [PreferenceBool.Style] of this item ([PreferenceBool.Style.Switch] or [PreferenceBool.Style.Checkbox])
 * @param value the value of this item
 * @param onValueChange the value changed callback of this item
 */
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
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle,
    itemSetup: PreferenceItemSetup = PreferenceBoolDefaults.itemSetup()
) {
    val onClick = if (LocalPreferenceSettings.current.toggleBooleanOnItemClick) {
        {
            val updated = !value
            onValueChange(updated)
        }
    } else null

    // Switch is larger than Checkbox and has 52x32 DP
    BasePreference(
        itemSetup = itemSetup,
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
                Checkbox(checked = value, onCheckedChange = {
                    onValueChange(it)
                })
            }

            PreferenceBool.Style.Switch -> {
                Switch(checked = value, onCheckedChange = {
                    onValueChange(it)
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

@Stable
object PreferenceBoolDefaults {
    @Composable
    fun itemSetup() = PreferenceItemSetup(
        trailingContentSize = PreferenceItemSetupDefaults.trailingContentSize(52.dp)
    )
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