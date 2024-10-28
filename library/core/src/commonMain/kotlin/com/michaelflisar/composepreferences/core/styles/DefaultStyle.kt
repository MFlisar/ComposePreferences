package com.michaelflisar.composepreferences.core.styles

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.classes.PreferenceItemStyleData
import com.michaelflisar.composepreferences.core.internal.PreferenceItemState

class DefaultStyle internal constructor(
    override val defaultItemStyle: PreferenceItemStyle,
    override val defaultSectionItemStyle: PreferenceItemStyle,
    override val defaultGroupItemStyle: PreferenceItemStyle,
    override val spacing: Dp
) : PreferenceStyle {

    companion object {

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
        }
    }

    @Composable
    override fun getItemStyle(
        item: PreferenceItemState.Item,
        itemStyle: PreferenceItemStyle
    ): PreferenceItemStyleData {
        return PreferenceItemStyleData(itemStyle, spacing)
    }
}