package com.michaelflisar.composepreferences.core.composables

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceType
import com.michaelflisar.composepreferences.core.helper.SearchText
import com.michaelflisar.composepreferences.core.internal.PreferenceItemState
import com.michaelflisar.composepreferences.core.internal.rememberPreferenceItemState
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle
import com.michaelflisar.composepreferences.core.styles.PreferenceStyleDefaults

/**
 * this is the default root composable that uses a [BasePreferenceContainer] and provides you with
 * a common default layout that consists of an area for an icon, a title, a sub title and some content
 *
 * &nbsp;
 *
 * In most cases this should be the root container for any custom preference.
 *
 * @param modifier the [Modifier] for this composable
 * @param enabled the [Dependency] that from which the enabled state is derived from
 * @param visible the [Dependency] that from which the visibility state is derived from
 * @param title the content of the title area
 * @param subtitle the content of the subtitle area
 * @param icon the content of the icon area
 * @param onClick the optional click listener for this item
 * @param onLongClick the optional long click listener for this item
 * @param itemStyle the [PreferenceItemStyle] of this this item - use [PreferenceStyleDefaults.item] and all the predefined styles in [PreferenceStyleDefaults] to provide your own style
 * @param itemSetup the [PreferenceItemSetup] of this this item which allows you to influence some of the behaviour and style specifically for a special item type
 * @param filterTags additional tags to title and sub title for the filter functionality
 * @param content the content of this composable
 */
@Composable
fun PreferenceScope.BasePreferenceCustom(
    modifier: Modifier = Modifier,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceItemSetup(),
    filterTags: List<String> = emptyList(),
    content: @Composable ColumnScope.() -> Unit
) {
    val tags = filterTags
    val item = rememberPreferenceItemState(PreferenceType.Item, visible, tags, itemSetup.excludeFromSectionStyle)

    BasePreferenceCustom(
        modifier = modifier,
        itemSetup = itemSetup,
        enabled = enabled,
        visible = visible,
        onClick = onClick,
        onLongClick = onLongClick,
        itemStyle = itemStyle,
        filterTags = tags,
        item = item,
        content = content
    )
}

@Composable
internal fun PreferenceScope.BasePreferenceCustom(
    modifier: Modifier = Modifier,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    item: PreferenceItemState.Item,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceItemSetup(),
    filterTags: List<String> = emptyList(),
    content: @Composable ColumnScope.() -> Unit
) {
    val preferenceSettings = LocalPreferenceSettings.current
    val style = preferenceSettings.style
    val itemStyle = style.getItemStyle(item, itemStyle)

    BasePreferenceContainer(
        modifier = modifier,
        enabled = enabled,
        visible = visible,
        onClick = onClick,
        onLongClick = onLongClick,
        itemStyle = itemStyle.style,
        filterTags = filterTags,
        item = item
    ) { modifier ->
        PreferenceItemCustom(
            modifier = modifier,
            preferenceStyle = itemStyle.style,
            setup = itemSetup,
            content = content
        )
        if (itemStyle.spaceBelow != 0.dp) {
            Spacer(modifier = Modifier.height(itemStyle.spaceBelow).fillMaxWidth())
        }
    }
}
