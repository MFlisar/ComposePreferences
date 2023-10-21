package com.michaelflisar.composepreferences.core.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.helper.AnimatedPreference
import com.michaelflisar.composepreferences.core.helper.disableState
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceItem
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

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
    content: @Composable (modifier: Modifier) -> Unit
) {
    val stateVisible = visible.state()
    val stateEnabled = enabled.state()
    val settings = LocalPreferenceSettings.current

    //println("stateVisible = $stateVisible | containerColor = ${preferenceStyle.colors.containerColor()}")

    PreferenceItem(group) {
        val animationFactor = remember {
            Animatable(1f)
        }
        LaunchedEffect(Unit) {
            if (stateVisible.value && settings.animationSpec != null) {
                animationFactor.animateTo(
                    0f,
                    animationSpec = settings.animationSpec
                )
            } else animationFactor.snapTo(0f)
        }

        AnimatedPreference(
            visible = stateVisible.value
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