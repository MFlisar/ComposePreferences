package com.michaelflisar.composepreferences.core

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.BasePreferenceCustom
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle

/**
 * A info preference item
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param content the custom content of this item
 */
@Composable
fun PreferenceScope.PreferenceCustom(
    // Base Preference
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceCustomDefaults.itemSetup(),
    filterTags: List<String> = emptyList(),
    // Special
    content: @Composable ColumnScope.() -> Unit,
) {
    BasePreferenceCustom(
        itemSetup = itemSetup,
        enabled = enabled,
        visible = visible,
        onLongClick = null,
        itemStyle = itemStyle,
        filterTags = filterTags,
        content = content
    )
}

@Stable
object PreferenceCustomDefaults {
    @Composable
    fun itemSetup() = PreferenceItemSetup()
}