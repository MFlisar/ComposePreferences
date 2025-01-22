package com.michaelflisar.composepreferences.core.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.ExperimentalSettings
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle
import com.michaelflisar.composepreferences.core.styles.PreferenceStyleDefaults
import com.michaelflisar.composepreferences.core.classes.PreferenceType
import com.michaelflisar.composepreferences.core.helper.AnimatedPreference
import com.michaelflisar.composepreferences.core.helper.disableState
import com.michaelflisar.composepreferences.core.internal.LocalItem
import com.michaelflisar.composepreferences.core.internal.PreferenceItemState
import com.michaelflisar.composepreferences.core.internal.rememberPreferenceItemState
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope

/**
 * this is the root composable that **MUST** be used by all preferences!
 *
 * &nbsp;
 *
 * In combination with [com.michaelflisar.composepreferences.core.PreferenceScreen] it takes care of the inner hierarchical data and
 * additionally it handles the enabled/visibiltiy state of the preference (depending on the dependencies as well as on the hierarchical position)
 * as well as the click and long click
 *
 * @param modifier the [Modifier] for this composable
 * @param enabled the [Dependency] that from which the enabled state is derived from
 * @param visible the [Dependency] that from which the visibility state is derived from
 * @param onClick the optional click listener for this item
 * @param onLongClick the optional long click listener for this item
 * @param type an **internal** flag to decide if this item holds sub items (and what type of sub items) or not (needed for the hierarchical data management)
 * @param itemStyle the [PreferenceItemStyle] of this this item - use [PreferenceStyleDefaults.item] and all the predefined styles in [PreferenceStyleDefaults] to provide your own style
 * @param filterTags the tags for filtering - those MUST contain title and subtitles as well here!
 * @param content the content of this composable
 */

@Composable
fun PreferenceScope.BasePreferenceContainer(
    modifier: Modifier = Modifier,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    filterTags: List<String> = emptyList(),
    content: @Composable (modifier: Modifier) -> Unit
) {
    val tags = filterTags
    val item = rememberPreferenceItemState(PreferenceType.Item, visible, tags, false)

    val preferenceSettings = LocalPreferenceSettings.current
    val style = preferenceSettings.style
    val itemStyle = style.getItemStyle(item, itemStyle)

    BasePreferenceContainer(
        modifier = modifier,
        enabled = enabled,
        visible = visible,
        item = item,
        onClick = onClick,
        onLongClick = onLongClick,
        itemStyle = itemStyle.style,
        filterTags = filterTags
    ) {
        content(modifier)
        if (itemStyle.spaceBelow != 0.dp) {
            Spacer(modifier = Modifier.height(itemStyle.spaceBelow).fillMaxWidth())
        }
    }
}

@Composable
internal fun PreferenceScope.BasePreferenceContainer(
    modifier: Modifier = Modifier,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    item: PreferenceItemState.Item,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    filterTags: List<String> = emptyList(),
    content: @Composable (modifier: Modifier) -> Unit
) {

    LocalItem.current.value = item

    val settings = LocalPreferenceSettings.current

    LaunchedEffect(filterTags, item.parent.tags) {
        item.allTags.value = filterTags + item.parent.tags
    }

    //println("container: item.id = ${item.id} | visible = ${item.visible.value} | filterTags = $filterTags | allTags = ${item.allTags.value}")

    // Item
    val animationFactor = remember {
        Animatable(1f)
    }
    LaunchedEffect(Unit) {
        if (item.visible.value && settings.animationSpec != null) {
            animationFactor.animateTo(
                0f,
                animationSpec = settings.animationSpec
            )
        } else animationFactor.snapTo(0f)
    }

    AnimatedPreference(
        itemState = item,
        animate = if (ExperimentalSettings.useAnimation) {
            settings.animationSpec != null
        } else false
    ) {
        Column(
            modifier = Modifier.padding(itemStyle.outerPadding)
        ) {
            content(
                createModifier(
                    modifier,
                    enabled,
                    onClick,
                    onLongClick,
                    itemStyle,
                    animationFactor
                )
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun createModifier(
    modifier: Modifier = Modifier,
    enabled: Dependency,
    onClick: (() -> Unit)?,
    onLongClick: (() -> Unit)?,
    itemStyle: PreferenceItemStyle,
    animationFactor: Animatable<Float, AnimationVector1D>
): Modifier {
    val stateEnabled = enabled.state()
    val animationOffsetY =
        with(LocalDensity.current) { (animationFactor.value * 8).dp.toPx() }
    val animationAlpha = 1f - animationFactor.value
    return modifier
        .clip(itemStyle.shape)
        .then(Modifier.disableState(stateEnabled))
        .then(if (stateEnabled.value && (onClick != null || onLongClick != null)) {
            if (onClick != null && onLongClick != null) {
                Modifier.combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                )
            } else if (onClick != null) {
                Modifier.clickable { onClick() }
            } else if (onLongClick != null) {
                Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            onLongClick()
                        }
                    )
                }
            } else Modifier
        } else Modifier)
        .graphicsLayer {
            translationY = animationOffsetY
            alpha = animationAlpha
        }
}