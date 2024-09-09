package com.michaelflisar.composepreferences.core

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.composables.PreviewPreference
import com.michaelflisar.composepreferences.core.helper.SearchText
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

/**
 * A info preference item
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param onLongClick a optional long click listener for this item
 */
@Composable
fun PreferenceScope.PreferenceInfo(
    // Special
    onLongClick: (() -> Unit)? = null,
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle,
    itemSetup: PreferenceItemSetup = PreferenceInfoDefaults.itemSetup(),
    filterTags: List<String> = emptyList()
) {
    BasePreference(
        itemSetup = itemSetup,
        enabled = enabled,
        visible = visible,
        title = title,
        subtitle = subtitle,
        icon = icon,
        onLongClick = onLongClick,
        preferenceStyle = preferenceStyle,
        content = null,
        filterTags = filterTags
    )
}

@Composable
fun PreferenceScope.PreferenceInfo(
    // Special
    onLongClick: (() -> Unit)? = null,
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle,
    itemSetup: PreferenceItemSetup = PreferenceInfoDefaults.itemSetup(),
    filterTags: List<String> = emptyList()
) {
    BasePreference(
        itemSetup = itemSetup,
        enabled = enabled,
        visible = visible,
        title = { SearchText(title) },
        subtitle = subtitle?.let { { SearchText(subtitle) } },
        icon = icon,
        onLongClick = onLongClick,
        preferenceStyle = preferenceStyle,
        content = null,
        filterTags = filterTags + listOfNotNull(title, subtitle)
    )
}

@Stable
object PreferenceInfoDefaults {
    @Composable
    fun itemSetup() = PreferenceItemSetup(ignoreMinItemHeight = false, alignment = Alignment.CenterVertically)
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun Preview() {
    PreviewPreference {
        PreferenceInfo(
            icon = { Icon(Icons.Default.Info, null) },
            title = { Text(text = "Info Preference") },
            subtitle = { Text(text = "This is a description") }
        )
    }
}

