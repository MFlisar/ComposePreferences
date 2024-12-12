package com.michaelflisar.composepreferences.core.styles

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.composables.PreferenceItemColors
import com.michaelflisar.composepreferences.core.composables.PreferenceItemDefaults

@Stable
object PreferenceStyleDefaults {

    private val DEFAULT_INNER_PADDING = PaddingValues(horizontal = 16.dp, 8.dp)

    /**
     * this function returns a [PreferenceItemStyle] defining the style of a preference item
     *
     * check out the overload to find a background/foreground based function
     *
     * @param colors the [PreferenceItemColors] of this item
     * @param innerPadding the outer [PaddingValues] of this item
     * @param outerPadding the inner [PaddingValues] of this item
     * @param shape the [Shape] of this item
     * @param tonalElevation the tonal elevation of this item
     * @param shadowElevation the shadow elevation of this item
     * @param titleTextStyle the shadow elevation of this item
     * @param subtitleTextStyle the shadow elevation of this item
     *
     * @return the [PreferenceItemStyle] holding the provided setup
     */
    @Composable
    fun item(
        colors: PreferenceItemColors = PreferenceItemDefaults.colors(),
        innerPadding: PaddingValues = DEFAULT_INNER_PADDING,
        outerPadding: PaddingValues = PaddingValues(),
        shape: Shape = LocalPreferenceSettings.current.style.defaultItemStyle.shape,
        tonalElevation: Dp = 0.dp,
        shadowElevation: Dp = 0.dp,
        titleTextStyle: TextStyle = MaterialTheme.typography.labelLarge,
        subtitleTextStyle: TextStyle = MaterialTheme.typography.bodyMedium
    ): PreferenceItemStyle = PreferenceItemStyle(
        colors = colors,
        innerPadding = innerPadding,
        outerPadding = outerPadding,
        shape = shape,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        titleTextStyle = titleTextStyle,
        subtitleTextStyle = subtitleTextStyle
    )

    /**
     * this function returns a [PreferenceItemStyle] defining the style of a preference item
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
     * @return the [PreferenceItemStyle] holding the provided setup
     */
    @Composable
    fun item(
        colorBackground: Color = MaterialTheme.colorScheme.surface,
        colorForeground: Color = MaterialTheme.colorScheme.onSurface,
        alphaVariant: Float = PreferenceItemDefaults.DEFAULT_ALPHA_VARIANT,
        shape: Shape = LocalPreferenceSettings.current.style.defaultItemStyle.shape,
        innerPadding: PaddingValues = DEFAULT_INNER_PADDING,
        outerPadding: PaddingValues = PaddingValues(),
        tonalElevation: Dp = 0.dp,
        shadowElevation: Dp = 0.dp,
        titleTextStyle: TextStyle = MaterialTheme.typography.labelLarge,
        subtitleTextStyle: TextStyle = MaterialTheme.typography.bodyMedium
    ): PreferenceItemStyle = PreferenceItemStyle(
        colors = PreferenceItemColors(
            containerColor = colorBackground,
            headlineColor = colorForeground,
            subHeadlineColor = colorForeground.copy(alpha = alphaVariant),
            leadingColor = colorForeground.copy(alpha = alphaVariant),
            contentColor = colorForeground.copy(alpha = alphaVariant)
        ),
        shape = shape,
        innerPadding = innerPadding,
        outerPadding = outerPadding,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        titleTextStyle = titleTextStyle,
        subtitleTextStyle = subtitleTextStyle
    )

    // -----------------
    // PREDEFINID Styles
    // -----------------

    /**
     * this function returns a [PreferenceItemStyle] with primary background
     *
     * @param shape the [Shape] of this item
     * @param alphaVariant the alpha value used to calculate the variant color from the provided colors
     *
     * @return the [PreferenceItemStyle] holding the provided setup
     */
    @Composable
    fun primary(
        shape: Shape = LocalPreferenceSettings.current.style.defaultItemStyle.shape,
        innerPadding: PaddingValues = DEFAULT_INNER_PADDING,
        outerPadding: PaddingValues = PaddingValues(),
        alphaVariant: Float = PreferenceItemDefaults.DEFAULT_ALPHA_VARIANT
    ) = item(
        colorBackground = MaterialTheme.colorScheme.primary,
        colorForeground = MaterialTheme.colorScheme.onPrimary,
        alphaVariant = alphaVariant,
        shape = shape,
        innerPadding = innerPadding,
        outerPadding = outerPadding
    )

    /**
     * this function returns a [PreferenceItemStyle] with primary container background
     *
     * @param shape the [Shape] of this item
     * @param alphaVariant the alpha value used to calculate the variant color from the provided colors
     *
     * @return the [PreferenceItemStyle] holding the provided setup
     */
    @Composable
    fun primaryContainer(
        shape: Shape = LocalPreferenceSettings.current.style.defaultItemStyle.shape,
        innerPadding: PaddingValues = DEFAULT_INNER_PADDING,
        outerPadding: PaddingValues = PaddingValues(),
        alphaVariant: Float = PreferenceItemDefaults.DEFAULT_ALPHA_VARIANT
    ) = item(
        colorBackground = MaterialTheme.colorScheme.primaryContainer,
        colorForeground = MaterialTheme.colorScheme.onPrimaryContainer,
        alphaVariant = alphaVariant,
        shape = shape,
        innerPadding = innerPadding,
        outerPadding = outerPadding
    )

    /**
     * this function returns a [PreferenceItemStyle] with error background
     *
     * @param shape the [Shape] of this item
     * @param alphaVariant the alpha value used to calculate the variant color from the provided colors
     *
     * @return the [PreferenceItemStyle] holding the provided setup
     */
    @Composable
    fun error(
        shape: Shape = LocalPreferenceSettings.current.style.defaultItemStyle.shape,
        innerPadding: PaddingValues = DEFAULT_INNER_PADDING,
        outerPadding: PaddingValues = PaddingValues(),
        alphaVariant: Float = PreferenceItemDefaults.DEFAULT_ALPHA_VARIANT
    ) = item(
        colorBackground = MaterialTheme.colorScheme.error,
        colorForeground = MaterialTheme.colorScheme.onError,
        alphaVariant = alphaVariant,
        shape = shape,
        innerPadding = innerPadding,
        outerPadding = outerPadding
    )

    /**
     * this function returns a [PreferenceItemStyle] with error container background
     *
     * @param shape the [Shape] of this item
     * @param alphaVariant the alpha value used to calculate the variant color from the provided colors
     *
     * @return the [PreferenceItemStyle] holding the provided setup
     */
    @Composable
    fun errorContainer(
        shape: Shape = LocalPreferenceSettings.current.style.defaultItemStyle.shape,
        innerPadding: PaddingValues = DEFAULT_INNER_PADDING,
        outerPadding: PaddingValues = PaddingValues(),
        alphaVariant: Float = PreferenceItemDefaults.DEFAULT_ALPHA_VARIANT
    ) = item(
        colorBackground = MaterialTheme.colorScheme.errorContainer,
        colorForeground = MaterialTheme.colorScheme.onErrorContainer,
        alphaVariant = alphaVariant,
        shape = shape,
        innerPadding = innerPadding,
        outerPadding = outerPadding
    )

    /**
     * this function returns a [PreferenceItemStyle] with surface variant background
     *
     * @param shape the [Shape] of this item
     * @param alphaVariant the alpha value used to calculate the variant color from the provided colors
     *
     * @return the [PreferenceItemStyle] holding the provided setup
     */
    @Composable
    fun surfaceVariant(
        shape: Shape = LocalPreferenceSettings.current.style.defaultItemStyle.shape,
        innerPadding: PaddingValues = DEFAULT_INNER_PADDING,
        outerPadding: PaddingValues = PaddingValues(),
    ): PreferenceItemStyle = item(
        colors = PreferenceItemColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            headlineColor = MaterialTheme.colorScheme.onSurface,
            subHeadlineColor = MaterialTheme.colorScheme.onSurfaceVariant,
            leadingColor = MaterialTheme.colorScheme.onSurfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = shape,
        innerPadding = innerPadding,
        outerPadding = outerPadding
    )

    /**
     * this function returns a [PreferenceItemStyle] with a header style (surface background + primary foreground)
     *
     * @param alphaVariant the alpha value used to calculate the variant color from the provided colors
     *
     * @return the [PreferenceItemStyle] holding the provided setup
     */
    @Composable
    fun header(
        alphaVariant: Float = PreferenceItemDefaults.DEFAULT_ALPHA_VARIANT,
        shape: Shape = LocalPreferenceSettings.current.style.defaultItemStyle.shape,
        innerPadding: PaddingValues = DEFAULT_INNER_PADDING,
        outerPadding: PaddingValues = PaddingValues(),
    ): PreferenceItemStyle = item(
        colors = PreferenceItemColors(
            containerColor = MaterialTheme.colorScheme.background,
            headlineColor = MaterialTheme.colorScheme.primary,
            subHeadlineColor = MaterialTheme.colorScheme.primary.copy(alpha = alphaVariant),
            leadingColor = MaterialTheme.colorScheme.primary.copy(alpha = alphaVariant),
            contentColor = MaterialTheme.colorScheme.primary.copy(alpha = alphaVariant)
        ),
        shape = shape,
        innerPadding = innerPadding,
        outerPadding = outerPadding
    )
}