package com.michaelflisar.composepreferences.core.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceSettings
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle
import com.michaelflisar.composepreferences.core.classes.PreferenceType
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup.TrailingContentSize

/**
 * this provides a class to define some custom setup to customise a preference item
 *
 * @param trailingContentSize the [TrailingContentSize] for a preference item
 * @param ignoreForceNoIconInset if true, the preference item will ignore the flag from [PreferenceSettings.forceNoIconInset]
 * @param ignoreMinItemHeight if true, the preference will not force a minimum item height
 * @param contentPlacementBottom if true, the content of this item will be place **below** the title/subtitle instead of **behind** it as trailing content
 * @param alignment the alignment of the preference item content
 */
@Stable
data class PreferenceItemSetup(
    val trailingContentSize: TrailingContentSize = TrailingContentSize(),
    val ignoreForceNoIconInset: Boolean = false,
    val ignoreMinItemHeight: Boolean = false,
    val contentPlacementBottom: Boolean = false,
    val alignment: Alignment.Vertical = Alignment.CenterVertically
) {

    /**
     * this provides a class to define size constraints for a preference item
     *
     * @param minWidth the minimum width of a preference item
     * @param maxWidth the maximun width of a preference item
     */
    @Stable
    class TrailingContentSize internal constructor(
        internal val minWidth: Dp,
        internal val maxWidth: Dp
    ) {
        internal constructor() : this(
            PreferenceItemSetupDefaults.DEFAULT_WIDTH,
            PreferenceItemSetupDefaults.DEFAULT_WIDTH
        )

    }
}

/**
 * this provides a class to define some custom INTERNAL settings to customise a preference item
 *
 * @param type a internal flag to define if this item does contain sub items or not and which type of sub items
 */
@Stable
class PreferenceItemSettings(
    val type: PreferenceType = PreferenceType.Item
)

internal fun Modifier.trailingContentSize(setup: TrailingContentSize) = this then
    if (setup.minWidth == 0.dp && setup.maxWidth == 0.dp) {
        Modifier
    } else if (setup.minWidth > 0.dp && setup.maxWidth > 0.dp) {
        Modifier.sizeIn(minWidth = setup.minWidth, maxWidth = setup.maxWidth)
    } else if (setup.minWidth > 0.dp) {
        Modifier.sizeIn(minWidth = setup.minWidth)
    } else if (setup.maxWidth > 0.dp) {
        Modifier.sizeIn(maxWidth = setup.maxWidth)
    } else Modifier

object PreferenceItemSetupDefaults {

    internal val DEFAULT_WIDTH = 96.dp

    /**
     * use this function to create a [TrailingContentSize] object
     *
     * @param width the fixed width of the trailing content - use 0 to disable it
     */
    @Composable
    fun trailingContentSize(
        width: Dp
    ) = TrailingContentSize(width, width)

    /**
     * use this function to create a [TrailingContentSize] object
     *
     * @param minWidth the minimum width of the trailing content - use 0 to disable it
     * @param maxWidth the maimum width of the trailing content - use 0 to disable it
     */
    @Composable
    fun trailingContentSize(
        minWidth: Dp,
        maxWidth: Dp
    ) = TrailingContentSize(minWidth, maxWidth)

    /**
     * use this function to get a uniform width for all contents showing a numeric value
     */
    @Composable
    fun numericContent() = TrailingContentSize(48.dp, 0.dp)

    /**
     * use this function to get a uniform width for all contents showing a date or time value
     */
    @Composable
    fun datetime() = TrailingContentSize(48.dp, 0.dp)

}

/**
 * see [PreferenceItemDefaults.colors] and its overloads
 */
@Immutable
class PreferenceItemColors internal constructor(
    private val containerColor: Color = Color.Unspecified,
    private val headlineColor: Color = Color.Unspecified,
    private val subHeadlineColor: Color = Color.Unspecified,
    private val leadingColor: Color = Color.Unspecified,
    private val contentColor: Color = Color.Unspecified
) {
    /** The container color of this [PreferenceItem] */
    @Composable
    internal fun containerColor(): State<Color> {
        return rememberUpdatedState(containerColor)
    }

    /** The color of this [PreferenceItem]'s headline text */
    @Composable
    internal fun headlineColor(): State<Color> {
        return rememberUpdatedState(
            headlineColor
        )
    }

    /** The color of this [PreferenceItem]'s leading content */
    @Composable
    internal fun leadingColor(): State<Color> {
        return rememberUpdatedState(
            leadingColor
        )
    }

    /** The color of this [PreferenceItem]'s sub headline text */
    @Composable
    internal fun subHeadlineColor(): State<Color> {
        return rememberUpdatedState(subHeadlineColor)
    }

    /** The color of this [PreferenceItem]'s content based on enabled state */
    @Composable
    internal fun contentColor(): State<Color> {
        return rememberUpdatedState(
            contentColor
        )
    }

    @Composable
    internal fun DecoratedLeadingContent(modifier: Modifier, content: @Composable () -> Unit) {
        val color = leadingColor()
        CompositionLocalProvider(LocalContentColor provides color.value) {
            Box(modifier) { content() }
        }
    }

    @Composable
    internal fun DecoratedHeadline(
        preferenceStyle: PreferenceItemStyle,
        content: @Composable () -> Unit
    ) {
        val color = headlineColor()
        val textStyle = preferenceStyle.titleTextStyle
        CompositionLocalProvider(LocalContentColor provides color.value) {
            val mergedStyle = LocalTextStyle.current.merge(textStyle)
            CompositionLocalProvider(LocalTextStyle provides mergedStyle, content = content)
        }
    }

    @Composable
    internal fun DecoratedSubHeadlineContent(
        preferenceStyle: PreferenceItemStyle,
        content: @Composable () -> Unit
    ) {
        val color = subHeadlineColor()
        val textStyle = preferenceStyle.subtitleTextStyle
        CompositionLocalProvider(LocalContentColor provides color.value) {
            val mergedStyle = LocalTextStyle.current.merge(textStyle)
            CompositionLocalProvider(LocalTextStyle provides mergedStyle, content = content)
        }
    }

    @Composable
    internal fun DecoratedContent(content: @Composable ColumnScope.() -> Unit, modifier: Modifier) {
        val color = contentColor()
        val textStyle = MaterialTheme.typography.labelMedium
        CompositionLocalProvider(LocalContentColor provides color.value) {
            val mergedStyle = LocalTextStyle.current.merge(textStyle)
            CompositionLocalProvider(LocalTextStyle provides mergedStyle) {
                Column(modifier) { content() }
            }
        }
    }
}

object PreferenceItemDefaults {

    /**
     * use this function to create a [PreferenceItemColors] object
     *
     * @param containerColor the [Color] of the container
     * @param contentColor the foreground [Color] of the content area
     * @param headlineColor the [Color] of the headline
     * @param subHeadlineColor the [Color] of the subheadline
     * @param leadingColor the foregorund [Color] of the leading content area
     */
    @Composable
    fun colors(
        containerColor: Color = MaterialTheme.colorScheme.surface,
        contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        headlineColor: Color = MaterialTheme.colorScheme.onSurface,
        subHeadlineColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        leadingColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    ): PreferenceItemColors =
        PreferenceItemColors(
            containerColor = containerColor,
            contentColor = contentColor,
            headlineColor = headlineColor,
            subHeadlineColor = subHeadlineColor,
            leadingColor = leadingColor
        )
}

@Composable
internal fun PreferenceItem(
    modifier: Modifier = Modifier,
    headline: @Composable () -> Unit,
    subHeadline: @Composable (() -> Unit)?,
    leading: @Composable (() -> Unit)?,
    content: @Composable (ColumnScope.() -> Unit)?,
    setup: PreferenceItemSetup,
    preferenceStyle: PreferenceItemStyle
) {
    val settings = LocalPreferenceSettings.current

    // why not "ListItem"?
    // * does not have any good constraints for the content size to header/text space
    //   => e.g. will a large trailing content hide the texts...
    // * provides more settings that we need (3 lines item and similar) + disabled colors (which is handled differently here)
    // => I need some fix text area to content area size constraints for some uniform and I don't want to use more colors than necessary
    //    so I simply make a own version based on ListItem myself...

    val minHeight =
        if (setup.ignoreMinItemHeight) 0.dp else if (subHeadline == null) 56.dp else 72.dp
    val rootPaddingValues = PaddingValues(
        horizontal = 16.dp,
        vertical = 8.dp
    )
    val contentPaddingValues = PaddingValues(horizontal = 0.dp)
    val leadingContentPaddingValues = PaddingValues(end = settings.leadingContentEndPadding)
    val trailingContentPaddingValues =
        PaddingValues(start = settings.trailingContentStartPadding, end = 8.dp)
    val bottomContentPaddingValues = PaddingValues(top = 8.dp)

    var leading = leading
    if (leading == null && settings.forceNoIconInset && !setup.ignoreForceNoIconInset) {
        leading = { Spacer(modifier.size(24.dp)) }
    }

    Surface(
        modifier = modifier,
        shape = preferenceStyle.shape,
        color = preferenceStyle.colors.containerColor().value,
        contentColor = preferenceStyle.colors.headlineColor().value,
        tonalElevation = preferenceStyle.tonalElevation,
        shadowElevation = preferenceStyle.shadowElevation,
    ) {
        Row(
            verticalAlignment = setup.alignment,
            modifier = Modifier
                .heightIn(min = minHeight)
                .padding(rootPaddingValues)
                .semantics(mergeDescendants = true) {}
        ) {

            // Leading Area
            leading?.let {
                preferenceStyle.colors.DecoratedLeadingContent(
                    Modifier.padding(leadingContentPaddingValues),
                    it
                )
            }

            // Center (Texts + Bottom Content)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(contentPaddingValues)
                    .sizeIn(minWidth = settings.minTextAreaWidth)
            ) {
                preferenceStyle.colors.DecoratedHeadline(preferenceStyle, headline)
                subHeadline?.let {
                    preferenceStyle.colors.DecoratedSubHeadlineContent(preferenceStyle, it)
                }
                if (content != null && setup.contentPlacementBottom) {
                    preferenceStyle.colors.DecoratedContent(
                        content,
                        Modifier.padding(bottomContentPaddingValues)
                    )
                }
            }

            // Content (Trailing)
            if (content != null && !setup.contentPlacementBottom) {
                Column(
                    modifier = Modifier
                        .padding(trailingContentPaddingValues)
                        .trailingContentSize(setup.trailingContentSize),
                    horizontalAlignment = Alignment.End
                ) {
                    preferenceStyle.colors.DecoratedContent(content, Modifier)
                }
            }
        }
    }

    // does not work that good and provides more settings that we need (3 lines item and similar) +
    // does not have any good constraints for the content size to header/text space
    //ListItem(
    //    modifier = modifier,
    //    headlineContent = headlineContent,
    //    supportingContent = supportingContent,
    //    leadingContent = leadingContent,
    //    trailingContent = trailingContent
    //)
}

