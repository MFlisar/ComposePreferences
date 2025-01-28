package com.michaelflisar.composepreferences.core.styles

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceItemStyleData
import com.michaelflisar.composepreferences.core.composables.PreferenceItemDefaults
import com.michaelflisar.composepreferences.core.internal.PreferenceItemState

class DefaultStyle internal constructor(
    override val defaultItemStyle: PreferenceItemStyle,
    override val defaultSectionItemStyle: PreferenceItemStyle,
    override val defaultGroupItemStyle: PreferenceItemStyle,
    override val spacing: Dp
) : PreferenceStyle {

    companion object {

        /* --8<-- [start: create] */
        /*
         * Creates a new DefaultStyle instance with the given parameters.
         *
         * This style does simply apply the defined styles to the items and sections.
         *
         * @param backgroundColor the background color of the preference items
         * @param foregroundColor the foreground color of the preference items (title color)
         * @param sectionBackgroundColor the background color of the preference sections
         * @param sectionForegroundColor the foreground color of the preference sections (title color)
         * @param shape the shape of the preference items
         * @param alphaVariant the alpha variant of the preference items (used for the content area and and subtitles in combination with the foreground color)
         * @param innerPadding the inner padding of the preference items
         * @param outerPadding the outer padding of the preference items
         * @param titleTextStyle the text style of the preference item titles
         * @param subtitleTextStyle the text style of the preference item subtitles
         * @param sectionTitleTextStyle the text style of the preference section titles
         * @param sectionSubtitleTextStyle the text style of the preference section subtitles
         * @param spacing the spacing between preference items
         */
        @Composable
        fun create(
            backgroundColor: Color = MaterialTheme.colorScheme.background,
            foregroundColor: Color = MaterialTheme.colorScheme.onBackground,
            sectionBackgroundColor: Color = MaterialTheme.colorScheme.background,
            sectionForegroundColor: Color = MaterialTheme.colorScheme.primary,
            shape: Shape = LocalPreferenceSettings.current.style.defaultItemStyle.shape,
            alphaVariant: Float = PreferenceItemDefaults.DEFAULT_ALPHA_VARIANT,
            innerPadding: PaddingValues = PreferenceStyleDefaults.DEFAULT_INNER_PADDING,
            outerPadding: PaddingValues = PreferenceStyleDefaults.DEFAULT_OUTER_PADDING,
            titleTextStyle: TextStyle = MaterialTheme.typography.labelLarge,
            subtitleTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
            sectionTitleTextStyle: TextStyle = MaterialTheme.typography.labelLarge,
            sectionSubtitleTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
            spacing: Dp = 0.dp
        ) : DefaultStyle
        /* --8<-- [end: create] */
        {
            val itemStyle = PreferenceStyleDefaults.item(
                colors = PreferenceItemDefaults.colors(
                    backgroundColor = backgroundColor,
                    foregroundColor = foregroundColor,
                    alphaVariant = alphaVariant
                ),
                innerPadding = innerPadding,
                outerPadding = outerPadding,
                shape = shape,
                tonalElevation = 0.dp,
                shadowElevation = 0.dp,
                titleTextStyle = titleTextStyle,
                subtitleTextStyle = subtitleTextStyle
            )
            val sectionItemStyle = PreferenceStyleDefaults.item(
                colors = PreferenceItemDefaults.colors(
                    backgroundColor = sectionBackgroundColor,
                    foregroundColor = sectionForegroundColor,
                    alphaVariant = alphaVariant
                ),
                shape = shape,
                tonalElevation = 0.dp,
                shadowElevation = 0.dp,
                innerPadding = innerPadding,
                outerPadding = outerPadding,
                titleTextStyle = sectionTitleTextStyle,
                subtitleTextStyle = sectionSubtitleTextStyle
            )
            return DefaultStyle(
                defaultItemStyle = itemStyle,
                defaultSectionItemStyle = sectionItemStyle,
                defaultGroupItemStyle = itemStyle,
                spacing = spacing
            )
        }

        /*
         * Creates a new DefaultStyle instance with the given parameters.
         *
         * @param defaultItemStyle the default style for preference items
         * @param defaultSectionItemStyle the default style for preference section items
         * @param defaultGroupItemStyle the default style for preference group items
         * @param spacing the spacing between preference items
         */
        /*
        @Composable
        fun create(
            defaultItemStyle: PreferenceItemStyle = PreferenceStyleDefaults.item(),
            defaultSectionItemStyle: PreferenceItemStyle = PreferenceStyleDefaults.header(),
            defaultGroupItemStyle: PreferenceItemStyle = PreferenceStyleDefaults.item(),
            spacing: Dp = 0.dp
        ): DefaultStyle {
            return DefaultStyle(
                defaultItemStyle,
                defaultSectionItemStyle,
                defaultGroupItemStyle,
                spacing
            )
        }*/
    }

    @Composable
    override fun getItemStyle(
        item: PreferenceItemState.Item,
        itemStyle: PreferenceItemStyle
    ): PreferenceItemStyleData {
        return PreferenceItemStyleData(itemStyle, spacing)
    }
}