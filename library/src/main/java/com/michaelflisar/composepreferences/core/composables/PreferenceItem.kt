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
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle

@Stable
class PreferenceItemSetup(
    val trailingContentSize: TrailingContentSize = TrailingContentSize(),
    val group: Boolean = false,
    val ignoreForceNoIconInset: Boolean = false,
    val ignoreMinItemHeight: Boolean = false,
    val contentPlacementBottom: Boolean = false,
    val alignment: Alignment.Vertical = Alignment.CenterVertically
) {
    @Stable
    class TrailingContentSize internal constructor(
        private val minWidth: Dp,
        private val maxWidth: Dp
    ) {

        internal constructor() : this(
            PreferenceItemSetupDefaults.DEFAULT_WIDTH,
            PreferenceItemSetupDefaults.DEFAULT_WIDTH
        )

        @Composable
        fun modifier() = if (minWidth == 0.dp && maxWidth == 0.dp) {
            Modifier
        } else if (minWidth > 0.dp && maxWidth > 0.dp) {
            Modifier.sizeIn(minWidth = minWidth, maxWidth = maxWidth)
        } else if (minWidth > 0.dp) {
            Modifier.sizeIn(minWidth = minWidth)
        } else if (maxWidth > 0.dp) {
            Modifier.sizeIn(maxWidth = maxWidth)
        } else Modifier

    }
}

object PreferenceItemSetupDefaults {

    internal val DEFAULT_WIDTH = 96.dp

    @Composable
    fun trailingContentSize(
        width: Dp
    ) = PreferenceItemSetup.TrailingContentSize(width, width)

    @Composable
    fun trailingContentSize(
        minWidth: Dp,
        maxWidth: Dp
    ) = PreferenceItemSetup.TrailingContentSize(minWidth, maxWidth)

    @Composable
    fun numericContent(
    ) = PreferenceItemSetup.TrailingContentSize(48.dp, 0.dp)

    @Composable
    fun datetime(
    ) = PreferenceItemSetup.TrailingContentSize(48.dp, 0.dp)

}


@Composable
internal fun PreferenceItem(
    modifier: Modifier = Modifier,
    headline: @Composable () -> Unit,
    subHeadline: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    content: @Composable (ColumnScope.() -> Unit)? = null,
    setup: PreferenceItemSetup,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle
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
                    it,
                    Modifier
                        .padding(leadingContentPaddingValues)
                )
            }

            // Center (Texts + Bottom Content)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(contentPaddingValues)
                    .sizeIn(minWidth = settings.minTextAreaWidth)
            ) {
                preferenceStyle.colors.DecoratedHeadline(headline)
                subHeadline?.let {
                    preferenceStyle.colors.DecoratedSubHeadlineContent(it)
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
                        .then(setup.trailingContentSize.modifier()),
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
    internal fun DecoratedLeadingContent(content: @Composable () -> Unit, modifier: Modifier) {
        val color = leadingColor()
        CompositionLocalProvider(LocalContentColor provides color.value) {
            Box(modifier) { content() }
        }
    }

    @Composable
    internal fun DecoratedHeadline(content: @Composable () -> Unit) {
        val color = headlineColor()
        val textStyle = MaterialTheme.typography.bodyLarge
        CompositionLocalProvider(LocalContentColor provides color.value) {
            val mergedStyle = LocalTextStyle.current.merge(textStyle)
            CompositionLocalProvider(LocalTextStyle provides mergedStyle, content = content)
        }
    }

    @Composable
    internal fun DecoratedSubHeadlineContent(content: @Composable () -> Unit) {
        val color = subHeadlineColor()
        val textStyle = MaterialTheme.typography.bodyMedium
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

object PreferenceItem {

}

object PreferenceItemDefaults {

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

    @Composable
    fun settings(): PreferenceSettings = PreferenceSettings()
}

