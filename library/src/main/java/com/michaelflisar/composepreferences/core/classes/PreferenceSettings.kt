package com.michaelflisar.composepreferences.core.classes

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalPreferenceSettings = compositionLocalOf { PreferenceSettings() }

data class PreferenceSettings internal constructor(
    val disabledStateAlpha: Float = .4f,
    val disabledStateGrayscale: Boolean = false,
    val toggleBooleanOnItemClick: Boolean = false,
    val maxLinesValue: Int = 2,
    val animationSpec: AnimationSpec<Float>? = tween(
        durationMillis = 200,
        easing = FastOutLinearInEasing
    ),
    val subScreenEndIndicator: @Composable (() -> Unit)? = {
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
    },
    val itemStyle: PreferenceStyle = PreferenceStyle(),
    val forceNoIconInset: Boolean = false,
    val minTextAreaWidth: Dp = 48.dp,
    val leadingContentEndPadding: Dp = 16.dp,
    val trailingContentStartPadding: Dp = 16.dp
)

object PreferenceSettingsDefaults {

    @Composable
    fun settings(
        disabledStateAlpha: Float = .4f,
        disabledStateGrayscale: Boolean = false,
        toggleBooleanOnItemClick: Boolean = false,
        maxLinesValue: Int = 2,
        animationSpec: AnimationSpec<Float>? = tween(
            durationMillis = 200,
            easing = FastOutLinearInEasing
        ),
        subScreenEndIndicator: @Composable (() -> Unit)? = {
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
        },
        itemStyle: PreferenceStyle = PreferenceStyleDefaults.item(),
        forceNoIconInset: Boolean = false,
        minTextAreaWidth: Dp = 48.dp,
        leadingContentEndPadding: Dp = 16.dp,
        trailingContentStartPadding: Dp = 16.dp
    ): PreferenceSettings = PreferenceSettings(
        disabledStateAlpha = disabledStateAlpha,
        disabledStateGrayscale = disabledStateGrayscale,
        toggleBooleanOnItemClick = toggleBooleanOnItemClick,
        maxLinesValue = maxLinesValue,
        animationSpec = animationSpec,
        subScreenEndIndicator = subScreenEndIndicator,
        itemStyle = itemStyle,
        forceNoIconInset = forceNoIconInset,
        minTextAreaWidth = minTextAreaWidth,
        leadingContentEndPadding = leadingContentEndPadding,
        trailingContentStartPadding = trailingContentStartPadding
    )
}
