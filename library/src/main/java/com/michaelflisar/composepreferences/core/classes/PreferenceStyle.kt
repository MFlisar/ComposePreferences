package com.michaelflisar.composepreferences.core.classes

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.composables.PreferenceItemColors
import com.michaelflisar.composepreferences.core.composables.PreferenceItemDefaults

@Stable
class PreferenceStyle internal constructor(
    val colors: PreferenceItemColors = PreferenceItemColors(),
    val shape: Shape = RectangleShape,
    val tonalElevation: Dp = 0.dp,
    val shadowElevation: Dp = 0.dp
)

@Stable
object PreferenceStyleDefaults {

    private const val DEFAULT_ALPHA_VARIANT = .6f

    @Composable
    fun item(
        colorBackground: Color = MaterialTheme.colorScheme.surface,
        colorForeground: Color = MaterialTheme.colorScheme.onSurface,
        alphaVariant: Float = DEFAULT_ALPHA_VARIANT,
        shape: Shape = RectangleShape,
        tonalElevation: Dp = 0.dp,
        shadowElevation: Dp = 0.dp
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
        shadowElevation
    )

    @Composable
    fun item(
        colors: PreferenceItemColors = PreferenceItemDefaults.colors(),
        shape: Shape = RectangleShape,
        tonalElevation: Dp = 0.dp,
        shadowElevation: Dp = 0.dp
    ): PreferenceStyle = PreferenceStyle(colors, shape, tonalElevation, shadowElevation)

    // -----------------
    // PREDEFINID Styles
    // -----------------

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

    @Composable
    fun surfaceVariant(
        shape: Shape = RectangleShape
    ): PreferenceStyle = PreferenceStyle(
        colors = PreferenceItemColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            headlineColor = MaterialTheme.colorScheme.onSurface,
            subHeadlineColor = MaterialTheme.colorScheme.onSurfaceVariant,
            leadingColor = MaterialTheme.colorScheme.onSurfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape
    )

    @Composable
    fun header(
        alphaVariant: Float = DEFAULT_ALPHA_VARIANT
    ): PreferenceStyle = PreferenceStyle(
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