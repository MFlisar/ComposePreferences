package com.michaelflisar.composepreferences.core

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.PreferenceType
import com.michaelflisar.composepreferences.core.composables.BasePreferenceContainer
import com.michaelflisar.composepreferences.core.internal.rememberPreferenceItemState
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope

/**
 * A divider preference item
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 */
@Composable
fun PreferenceScope.PreferenceDivider(
    // Special
    // Base Preference
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    filterTags: List<String> = emptyList()
) {
    val tags = filterTags
    val item = rememberPreferenceItemState(PreferenceType.Item, visible, tags)

    BasePreferenceContainer(
        modifier = Modifier,
        enabled = enabled,
        visible = visible,
        onClick = null,
        filterTags = tags,
        item = item,
    ) { modifier ->
        HorizontalDivider(modifier = modifier)
    }
}