package com.michaelflisar.composepreferences.core.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceFilter
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.classes.PreferenceStyleDefaults
import com.michaelflisar.composepreferences.core.helper.AnimatedPreference
import com.michaelflisar.composepreferences.core.helper.disableState
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceItem
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

/**
 * this is the root composable that **MUST** best used by all preferences!
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
 * @param group an **internal** flag to decide if this item holds sub items or not (needed for the hierarchical data)
 * @param preferenceStyle the [PreferenceStyle] of this this item - use [PreferenceStyleDefaults.item] and all the predefined styles in [PreferenceStyleDefaults] to provide your own style
 * @param content the content of this composable
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PreferenceScope.BasePreferenceContainer(
    modifier: Modifier = Modifier,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    group: Boolean = false,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle,
    filterTags: List<String> = emptyList(),
    content: @Composable (modifier: Modifier) -> Unit
) {
    val stateVisible = visible.state()
    val stateEnabled = enabled.state()
    val settings = LocalPreferenceSettings.current
    val filter = LocalPreferenceFilter.current

    val visibleInFilter by remember(filter?.search?.value, filterTags) {
        derivedStateOf {
            filter?.filter(filter.search.value, filterTags) ?: true
        }
    }
    val visible by remember(stateVisible.value, visibleInFilter) {
        derivedStateOf {
            visibleInFilter && stateVisible.value
        }
    }

    //println("stateVisible = $stateVisible | containerColor = ${preferenceStyle.colors.containerColor()}")

    PreferenceItem(group) {
        val animationFactor = remember {
            Animatable(1f)
        }
        LaunchedEffect(Unit) {
            if (visible && settings.animationSpec != null) {
                animationFactor.animateTo(
                    0f,
                    animationSpec = settings.animationSpec
                )
            } else animationFactor.snapTo(0f)
        }

        AnimatedPreference(
            visible = visible
        ) {
            val animationOffsetY =
                with(LocalDensity.current) { (animationFactor.value * 8).dp.toPx() }
            val animationAlpha = 1f - animationFactor.value
            val modifier = modifier
                .clip(preferenceStyle.shape)
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
            content(modifier)
        }
    }
}