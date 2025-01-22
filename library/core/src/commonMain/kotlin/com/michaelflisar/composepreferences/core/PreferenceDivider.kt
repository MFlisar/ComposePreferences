package com.michaelflisar.composepreferences.core

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.BasePreferenceCustom
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle

/* --8<-- [start: constructor] */
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
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    filterTags: List<String> = emptyList()
)
/* --8<-- [end: constructor] */
{
    val itemSetup = PreferenceInfoDefaults.itemSetup().copy(
        hideTitle = true,
        minHeight = 0.dp,
        contentPlacementBottom = true,
        excludeFromSectionStyle = true,
        ignoreForceNoIconInset = true
    )
    BasePreferenceCustom(
        itemSetup = itemSetup,
        enabled = enabled,
        visible = visible,
        onClick = null,
        onLongClick = null,
        itemStyle = itemStyle,
        filterTags = filterTags
    ){
        HorizontalDivider()
    }

    //BasePreference(
    //    itemSetup = itemSetup,
    //    enabled = enabled,
    //    visible = visible,
    //    title = "",
    //    subtitle = null,
    //    icon = null,
    //    onLongClick = null,
    //    itemStyle = itemStyle,
    //    filterTags = filterTags
    //) {
    //    HorizontalDivider()
    //}
    /*
        val tags = filterTags
        val item = rememberPreferenceItemState(PreferenceType.Item, visible, tags, false)
        BasePreferenceContainer(
            modifier = Modifier,
            enabled = enabled,
            visible = visible,
            onClick = null,
            filterTags = tags,
            item = item,
        ) { modifier ->
            HorizontalDivider(modifier = modifier)
        }*/
}