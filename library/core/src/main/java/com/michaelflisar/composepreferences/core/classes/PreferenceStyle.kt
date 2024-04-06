package com.michaelflisar.composepreferences.core.classes

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.composables.PreferenceItemColors
import com.michaelflisar.composepreferences.core.composables.PreferenceItemDefaults

/**
 * see [PreferenceStyleDefaults.item]
 */
@Stable
class PreferenceStyle internal constructor(
    val colors: PreferenceItemColors = PreferenceItemColors(),
    val shape: Shape = RectangleShape,
    val tonalElevation: Dp = 0.dp,
    val shadowElevation: Dp = 0.dp,
    val titleTextStyle: TextStyle = TextStyle.Default,
    val subtitleTextStyle: TextStyle = TextStyle.Default
)

@Stable
object PreferenceStyleDefaults {

    private const val DEFAULT_ALPHA_VARIANT = .6f

    /**
     * this function returns a [PreferenceStyle] defining the style of a preference item
     *
     * check out the overload to find a background/foreground based function
     *
     * @param colors the [PreferenceItemColors] of this item
     * @param shape the [Shape] of this item
     * @param tonalElevation the tonal elevation of this item
     * @param shadowElevation the shadow elevation of this item
     * @param titleTextStyle the shadow elevation of this item
     * @param subtitleTextStyle the shadow elevation of this item
     *
     * @return the [PreferenceStyle] holding the provided setup
     */
    @Composable
    fun item(
        colors: PreferenceItemColors = PreferenceItemDefaults.colors(),
        shape: Shape = RectangleShape,
        tonalElevation: Dp = 0.dp,
        shadowElevation: Dp = 0.dp,
        titleTextStyle: TextStyle = MaterialTheme.typography.labelLarge,
        subtitleTextStyle: TextStyle = MaterialTheme.typography.bodyMedium
    ): PreferenceStyle = PreferenceStyle(colors, shape, tonalElevation, shadowElevation, titleTextStyle, subtitleTextStyle)

    /**
     * this function returns a [PreferenceStyle] defining the style of a preference item
     *
     * check out the overload to find a background/foreground based function
     *
     * @param colorBackground the background [Color] of this item
     * @param colorForeground the foreground [Color] of this item
     * @param alphaVariant the alpha value used to calculate the variant color from the provided colors
     * @param shape the [Shape] of this item
     * @param tonalElevation the tonal elevation of this item
     * @param shadowElevation the shadow elevation of this item
     * @param titleTextStyle the shadow elevation of this item
     * @param subtitleTextStyle the shadow elevation of this item
     *
     * @return the [PreferenceStyle] holding the provided setup
     */
    @Composable
    fun item(
        colorBackground: Color = MaterialTheme.colorScheme.surface,
        colorForeground: Color = MaterialTheme.colorScheme.onSurface,
        alphaVariant: Float = DEFAULT_ALPHA_VARIANT,
        shape: Shape = RectangleShape,
        tonalElevation: Dp = 0.dp,
        shadowElevation: Dp = 0.dp,
        titleTextStyle: TextStyle = MaterialTheme.typography.labelLarge,
        subtitleTextStyle: TextStyle = MaterialTheme.typography.bodyMedium
    ): PreferenceStyle = PreferenceStyle(
        colors = PreferenceItemColors(
            containerColor = colorBackground,
            headlineColor = colorForeground,
            subHeadlineColor = colorForeground.copy(alpha = alphaVariant),
            leadingColor = colorForeground.copy(alpha = alphaVariant),
            contentColor = colorForeground.copy(alpha = alphaVariant)
        ),
        shape,
        tonalElevation,
        shadowElevation,
        titleTextStyle,
        subtitleTextStyle
    )

    // -----------------
    // PREDEFINID Styles
    // -----------------

    /**
     * this function returns a [PreferenceStyle] with primary background
     *
     * @param shape the [Shape] of this item
     * @param alphaVariant the alpha value used to calculate the variant color from the provided colors
     *
     * @return the [PreferenceStyle] holding the provided setup
     */
    @Composable
    fun primary(
        shape: Shape = RectangleShape,
        alphaVariant: Float = DEFAULT_ALPHA_VARIANT
    ) = item(
        colorBackground = MaterialTheme.colorScheme.primary,
        colorForeground = MaterialTheme.colorScheme.onPrimary,
        alphaVariant = alphaVariant,
        shape = shape
    )

    /**
     * this function returns a [PreferenceStyle] with primary container background
     *
     * @param shape the [Shape] of this item
     * @param alphaVariant the alpha value used to calculate the variant color from the provided colors
     *
     * @return the [PreferenceStyle] holding the provided setup
     */
    @Composable
    fun primaryContainer(
        shape: Shape = RectangleShape,
        alphaVariant: Float = DEFAULT_ALPHA_VARIANT
    ) = item(
        colorBackground = MaterialTheme.colorScheme.primaryContainer,
        colorForeground = MaterialTheme.colorScheme.onPrimaryContainer,
        alphaVariant = alphaVariant,
        shape = shape
    )

    /**
     * this function returns a [PreferenceStyle] with error background
     *
     * @param shape the [Shape] of this item
     * @param alphaVariant the alpha value used to calculate the variant color from the provided colors
     *
     * @return the [PreferenceStyle] holding the provided setup
     */
    @Composable
    fun error(
        shape: Shape = RectangleShape,
        alphaVariant: Float = DEFAULT_ALPHA_VARIANT
    ) = item(
        colorBackground = MaterialTheme.colorScheme.error,
        colorForeground = MaterialTheme.colorScheme.onError,
        alphaVariant = alphaVariant,
        shape = shape
    )

    /**
     * this function returns a [PreferenceStyle] with error container background
     *
     * @param shape the [Shape] of this item
     * @param alphaVariant the alpha value used to calculate the variant color from the provided colors
     *
     * @return the [PreferenceStyle] holding the provided setup
     */
    @Composable
    fun errorContainer(
        shape: Shape = RectangleShape,
        alphaVariant: Float = DEFAULT_ALPHA_VARIANT
    ) = item(
        colorBackground = MaterialTheme.colorScheme.errorContainer,
        colorForeground = MaterialTheme.colorScheme.onErrorContainer,
        alphaVariant = alphaVariant,
        shape = shape
    )

    /**
     * this function returns a [PreferenceStyle] with surface variant background
     *
     * @param shape the [Shape] of this item
     * @param alphaVariant the alpha value used to calculate the variant color from the provided colors
     *
     * @return the [PreferenceStyle] holding the provided setup
     */
    @Composable
    fun surfaceVariant(
        shape: Shape = RectangleShape
    ): PreferenceStyle = item(
        colors = PreferenceItemColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            headlineColor = MaterialTheme.colorScheme.onSurface,
            subHeadlineColor = MaterialTheme.colorScheme.onSurfaceVariant,
            leadingColor = MaterialTheme.colorScheme.onSurfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape
    )

    /**
     * this function returns a [PreferenceStyle] with a header style (surface background + primary foreground)
     *
     * @param alphaVariant the alpha value used to calculate the variant color from the provided colors
     *
     * @return the [PreferenceStyle] holding the provided setup
     */
    @Composable
    fun header(
        alphaVariant: Float = DEFAULT_ALPHA_VARIANT
    ): PreferenceStyle = item(
        colors = PreferenceItemColors(
            containerColor = MaterialTheme.colorScheme.surface,
            headlineColor = MaterialTheme.colorScheme.primary,
            subHeadlineColor = MaterialTheme.colorScheme.primary.copy(alpha = alphaVariant),
            leadingColor = MaterialTheme.colorScheme.primary.copy(alpha = alphaVariant),
            contentColor = MaterialTheme.colorScheme.primary.copy(alpha = alphaVariant)
        ),
        RectangleShape
    )
}