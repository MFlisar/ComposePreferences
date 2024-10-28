package com.michaelflisar.composepreferences.core.styles

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.composables.PreferenceItemColors

/**
 * see [PreferenceStyleDefaults.item]
 */
@Stable
data class PreferenceItemStyle internal constructor(
    val colors: PreferenceItemColors = PreferenceItemColors(),
    val padding: PaddingValues = PaddingValues(),
    val shape: Shape = RectangleShape,
    val tonalElevation: Dp = 0.dp,
    val shadowElevation: Dp = 0.dp,
    val titleTextStyle: TextStyle = TextStyle.Default,
    val subtitleTextStyle: TextStyle = TextStyle.Default
)

