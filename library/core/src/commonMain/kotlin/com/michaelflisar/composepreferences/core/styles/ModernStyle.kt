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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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

        @Composable
        fun create(
            cornerSize: Dp = 8.dp,
            horizontalInnerItemPadding: Dp = 16.dp,
            horizontalOuterItemPadding: Dp = 16.dp,
            sectionColors: PreferenceItemColors = PreferenceItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            defaultItemStyle : PreferenceItemStyle = PreferenceStyleDefaults.item(
                shape = RoundedCornerShape(cornerSize),
                innerPadding = PaddingValues(horizontal = horizontalInnerItemPadding, vertical = 8.dp),
                outerPadding = PaddingValues(horizontal = horizontalOuterItemPadding),
            ),
            defaultSectionItemStyle : PreferenceItemStyle = PreferenceStyleDefaults.header(
                shape = RoundedCornerShape(cornerSize),
                innerPadding = PaddingValues(horizontal = horizontalInnerItemPadding, vertical = 8.dp),
                outerPadding = PaddingValues(horizontal = horizontalOuterItemPadding),
            ),
            defaultGroupItemStyle : PreferenceItemStyle = PreferenceStyleDefaults.item(
                shape = RoundedCornerShape(cornerSize),
                innerPadding = PaddingValues(horizontal = horizontalInnerItemPadding, vertical = 8.dp),
                outerPadding = PaddingValues(horizontal = horizontalOuterItemPadding),
            ),
            spacing: Dp = 4.dp
        ) : ModernStyle {
            return ModernStyle(
                PreferenceSectionStyle(cornerSize, sectionColors),
                defaultItemStyle,
                defaultSectionItemStyle,
                defaultGroupItemStyle,
                spacing
            )
        }
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
        val isInSection = item.parent is PreferenceItemState.Item && item.parent.type == PreferenceType.Section

        val style =
            if (isInSection) {
                var cornerTop = 0.dp
                var cornerBottom = 0.dp

                val visibleChildren by remember {
                    derivedStateOf {
                        item.parent.children.value.filter { it.visible.value }
                    }
                }

                if (visibleChildren.firstOrNull() == item) {
                    cornerTop = sectionItemStyle.cornerSize
                }
                if (visibleChildren.lastOrNull() == item) {
                    cornerBottom = sectionItemStyle.cornerSize
                }

                val cornerRadiusTop by animateDpAsState(targetValue = cornerTop, label = "")
                val cornerRadiusBottom by animateDpAsState(targetValue = cornerBottom, label = "")
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
            } else if (item.type == PreferenceType.Section && item.children.value.isNotEmpty()) {
                itemStyle
            } else itemStyle

        val spacerBelow = !isLastVisibleItem.value
        return PreferenceItemStyleData(style, if (spacerBelow) spacing else 0.dp)
    }
}