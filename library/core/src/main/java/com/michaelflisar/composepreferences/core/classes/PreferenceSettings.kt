package com.michaelflisar.composepreferences.core.classes

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalPreferenceSettings = compositionLocalOf { PreferenceSettings() }

/**
 * see [PreferenceSettingsDefaults.settings]
 */
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
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
    },
    val itemStyle: PreferenceStyle = PreferenceStyle(),
    val forceNoIconInset: Boolean = false,
    val minTextAreaWidth: Dp = 48.dp,
    val leadingContentEndPadding: Dp = 16.dp,
    val trailingContentStartPadding: Dp = 16.dp
)

object PreferenceSettingsDefaults {

    /**
     * use this function to create a [PreferenceSettings] object
     *
     * @param disabledStateAlpha this value should be between [0f, 1f] and defines the alpha value which which a disabled preference item is blended
     * @param disabledStateGrayscale if true, a disabled preference will grayscale its content
     * @param toggleBooleanOnItemClick if true, clicking a boolean preference will toggle its state, otherwise only clicks on the checkbox/switch will toggle it
     * @param maxLinesValue the maximum lines of texts for the content area of a preference
     * @param animationSpec a optional [AnimationSpec] that defines the animation for preferences on initial display - use [null] to disable the animation
     * @param subScreenEndIndicator a optional [Composable] that defines a trailing indicator for sub screen preferences - use [null] to disable it
     * @param itemStyle the default [PreferenceStyle] that should be used by all preferences - use [PreferenceStyleDefaults.item] or any of the other predefined styles inside [PreferenceStyleDefaults]
     * @param forceNoIconInset enable this flag to inset items correctly to align with other items with icons
     * @param minTextAreaWidth minimum width or the text area (the title/subtitle area)
     * @param leadingContentEndPadding the padding between the leading content and the title/subtitle area
     * @param trailingContentStartPadding the padding between the title/subtitle area and the trailing content area
     */
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
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
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
