package com.michaelflisar.composepreferences.core.styles

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.michaelflisar.composepreferences.core.classes.PreferenceItemStyleData
import com.michaelflisar.composepreferences.core.internal.PreferenceItemState

interface PreferenceStyle {

    val spacing: Dp
    val defaultItemStyle : PreferenceItemStyle
    val defaultSectionItemStyle : PreferenceItemStyle
    val defaultGroupItemStyle : PreferenceItemStyle

    @Composable
    fun getItemStyle(
        item: PreferenceItemState.Item,
        itemStyle: PreferenceItemStyle
    ): PreferenceItemStyleData

}