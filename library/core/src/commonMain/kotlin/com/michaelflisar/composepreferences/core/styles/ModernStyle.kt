package com.michaelflisar.composepreferences.core.styles

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceItemStyleData
import com.michaelflisar.composepreferences.core.classes.PreferenceType
import com.michaelflisar.composepreferences.core.composables.PreferenceItemColors
import com.michaelflisar.composepreferences.core.composables.PreferenceItemDefaults
import com.michaelflisar.composepreferences.core.internal.PreferenceItemState

class ModernStyle internal constructor(
    private val sectionItemStyle: PreferenceSectionStyle,
    override val defaultItemStyle : PreferenceItemStyle,
    override val defaultSectionItemStyle : PreferenceItemStyle,
    override val defaultGroupItemStyle : PreferenceItemStyle,
    override val spacing: Dp
) : PreferenceStyle {

    companion object {

        val DEFAULT_OUTER_PADDING = PaddingValues(horizontal = 8.dp)
        val DEFAULT_ITEM_SPACING = 4.dp
        val DEFAULT_CORNER_SIZE = 8.dp

        /* --8<-- [start: create] */
        /*
        * Creates a new ModernStyle instance with the given parameters.
        *
        * @param cornerSize the corner size for preference items (used for selector as well as for the grouping style of items inside sections)
        * @param backgroundColor the background color of the preference items
        * @param foregroundColor the foreground color of the preference items (title color)
        * @param sectionBackgroundColor the background color of the preference sections
        * @param sectionForegroundColor the foreground color of the preference sections (title color)
        * @param sectionGroupItemBackgroundColor the background color used for items inside sections
        * @param sectionGroupItemForegroundColor the foreground color used for items inside sections
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
            cornerSize: Dp = DEFAULT_CORNER_SIZE,
            backgroundColor: Color = MaterialTheme.colorScheme.background,
            foregroundColor: Color = MaterialTheme.colorScheme.onBackground,
            sectionBackgroundColor: Color = MaterialTheme.colorScheme.background,
            sectionForegroundColor: Color = MaterialTheme.colorScheme.primary,
            sectionGroupItemBackgroundColor: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
            sectionGroupItemForegroundColor: Color = MaterialTheme.colorScheme.onSurface,
            alphaVariant: Float = PreferenceItemDefaults.DEFAULT_ALPHA_VARIANT,
            innerPadding: PaddingValues = PreferenceStyleDefaults.DEFAULT_INNER_PADDING,
            outerPadding: PaddingValues = DEFAULT_OUTER_PADDING,
            titleTextStyle: TextStyle = MaterialTheme.typography.labelLarge,
            subtitleTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
            sectionTitleTextStyle: TextStyle = MaterialTheme.typography.labelLarge,
            sectionSubtitleTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
            spacing: Dp = DEFAULT_ITEM_SPACING
        ) : ModernStyle
        /* --8<-- [end: create] */
        {

            val shape = RoundedCornerShape(cornerSize)

            val sectionColors = PreferenceItemDefaults.colors(
                backgroundColor = sectionGroupItemBackgroundColor,
                foregroundColor = sectionGroupItemForegroundColor,
                alphaVariant = alphaVariant
            )

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

            return ModernStyle(
                PreferenceSectionStyle(cornerSize, sectionColors),
                itemStyle,
                sectionItemStyle,
                itemStyle,
                spacing
            )
        }

        /*
        * Creates a new ModernStyle instance with the given parameters.
        *
        * @param cornerSize the corner size for preference items (used for selector as well as for the grouping style of items inside sections)
        * @param innerPadding the inner padding for preference items
        * @param outerPadding the outer padding for preference items
        * @param sectionGroupItemColors the colors for items inside sections (used in combination with cornerSize to visually group items inside sections)
        * @param defaultItemStyle the default style for preference items
        * @param defaultSectionItemStyle the default style for preference section items
        * @param defaultGroupItemStyle the default style for preference group items
        * @param spacing the spacing between preference items
        */
        /*
        @Composable
        fun create(
            cornerSize: Dp = DEFAULT_CORNER_SIZE,
            innerPadding: PaddingValues = PreferenceStyleDefaults.DEFAULT_INNER_PADDING,
            outerPadding: PaddingValues = DEFAULT_OUTER_PADDING,
            sectionGroupItemColors: PreferenceItemColors = PreferenceItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            defaultItemStyle : PreferenceItemStyle = PreferenceStyleDefaults.item(
                shape = RoundedCornerShape(cornerSize),
                innerPadding = innerPadding,
                outerPadding = outerPadding
            ),
            defaultSectionItemStyle : PreferenceItemStyle = PreferenceStyleDefaults.header(
                shape = RoundedCornerShape(cornerSize),
                innerPadding = innerPadding,
                outerPadding = outerPadding
            ),
            defaultGroupItemStyle : PreferenceItemStyle = PreferenceStyleDefaults.item(
                shape = RoundedCornerShape(cornerSize),
                innerPadding = innerPadding,
                outerPadding = outerPadding
            ),
            spacing: Dp = DEFAULT_ITEM_SPACING
        ) : ModernStyle {
            return ModernStyle(
                PreferenceSectionStyle(cornerSize, sectionGroupItemColors),
                defaultItemStyle,
                defaultSectionItemStyle,
                defaultGroupItemStyle,
                spacing
            )
        }*/
    }

    data class PreferenceSectionStyle(
        val cornerSize: Dp,
        val colors: PreferenceItemColors
    )

    @Composable
    override fun getItemStyle(
        item: PreferenceItemState.Item,
        itemStyle: PreferenceItemStyle
    ): PreferenceItemStyleData {

        /*
        val isLastVisibleItem = remember {
            derivedStateOf {

                var last = item.parent.children.value.lastOrNull { it.visible.value } == item

                //println("1 - item = ${item.tags} | last = $last")

                if (item.type == PreferenceType.Section) {
                    // Item is a section => it is only the last item if it does not have any children!
                    if (item.children.value.isNotEmpty()) {
                        last = false
                    }
                }

                if ((item.parent as? PreferenceItemState.Item)?.type == PreferenceType.Section) {
                    // parent is a section => we check if the section is the last item of its parent
                    val section = item.parent
                    last = last && section.parent.children.value.lastOrNull { it.visible.value } == section
                }

                //println("2 - item.parent = ${item.parent.tags} | item = ${item.tags} | last = $last")

                last
            }
        }
         */

        val isInSection = item.parent is PreferenceItemState.Item && item.parent.type == PreferenceType.Section
        //println("3 - item = ${item.tags} | isInSection = $isInSection | isLastVisibleItem = ${isLastVisibleItem.value}")

        val style =
            if (isInSection) {
                if (item.excludeFromSectionStyle) {
                    itemStyle
                } else {
                    var cornerTop = 0.dp
                    var cornerBottom = 0.dp

                    val visibleChildren by remember {
                        derivedStateOf {
                            item.parent.children.value.filter { it.visible.value }
                        }
                    }

                    if (visibleChildren.firstOrNull() == item || visibleChildren.previousOrNull(item)?.excludeFromSectionStyle == true) {
                        cornerTop = sectionItemStyle.cornerSize
                    }
                    if (visibleChildren.lastOrNull() == item || visibleChildren.nextOrNull(item)?.excludeFromSectionStyle == true) {
                        cornerBottom = sectionItemStyle.cornerSize
                    }

                    val cornerRadiusTop by animateDpAsState(targetValue = cornerTop, label = "")
                    val cornerRadiusBottom by animateDpAsState(
                        targetValue = cornerBottom,
                        label = ""
                    )
                    val shape = RoundedCornerShape(
                        topStart = cornerRadiusTop,
                        topEnd = cornerRadiusTop,
                        bottomStart = cornerRadiusBottom,
                        bottomEnd = cornerRadiusBottom,
                    )

                    itemStyle.copy(
                        shape = shape,
                        colors = sectionItemStyle.colors
                    )
                }
            } else if (item.type == PreferenceType.Section && item.children.value.isNotEmpty()) {
                itemStyle
            } else itemStyle

        //val spacerBelow = !isLastVisibleItem.value
        //println("4 - item = ${item.tags} | spacerBelow = $spacerBelow")

        return PreferenceItemStyleData(style, spacing)//if (spacerBelow) spacing else 0.dp)

    }
}

private fun <T> List<T>.previousOrNull(item: T): T? {
    val index = indexOf(item)
    return if (index > 0)
        get(index - 1)
    else null
}

private fun <T> List<T>.nextOrNull(item: T): T? {
    val index = indexOf(item)
    return if (index < size - 1)
        get(index + 1)
    else null
}