package com.michaelflisar.composepreferences.screen.bool

import androidx.compose.material3.Checkbox
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetupDefaults
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle

/* --8<-- [start: constructor] */
/**
 * A bool preference item - this item shows a checkbox/switch which reflects the preference state
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param style the [PreferenceBool.Style] of this item ([PreferenceBool.Style.Switch] or [PreferenceBool.Style.Checkbox])
 * @param value the [MutableState] of this item
 */
@Composable
fun PreferenceScope.PreferenceBool(
    style: PreferenceBool.Style = PreferenceBool.Style.Switch,
    // Special
    value: MutableState<Boolean>,
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceBoolDefaults.itemSetup(),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList()
)
/* --8<-- [end: constructor] */
{
    PreferenceBool(
        style = style,
        value = value.value,
        onValueChange = { value.value = it },
        title = title,
        enabled = enabled,
        visible = visible,
        subtitle = subtitle,
        icon = icon,
        itemStyle = itemStyle,
        itemSetup = itemSetup,
        titleRenderer = titleRenderer,
        subtitleRenderer = subtitleRenderer,
        filterTags = filterTags
    )
}

/* --8<-- [start: constructor2] */
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
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceBoolDefaults.itemSetup(),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList()
)
/* --8<-- [end: constructor] */
{
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
        itemStyle = itemStyle,
        titleRenderer = titleRenderer,
        subtitleRenderer = subtitleRenderer,
        filterTags = filterTags,
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