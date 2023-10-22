package com.michaelflisar.composepreferences.core.composables

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.classes.PreferenceStyleDefaults

/**
 * this is the default text content composable for all preferences which does automatically apply
 * all settings to this text (e.g. maxLines, alignment, ...)
 *
 * &nbsp;
 *
 * In most cases this should be used by any custom preference if it shows some text inside its content area.
 * It offers all settings of a default [Text] that are not specifically defined by the local [PreferenceSettings]
 *
 * @param text the text
 * @param modifier the [Modifier] for this composable
 * @param color a custom [Color]
 * @param fontSize a custom [TextUnit]
 * @param fontStyle a custom [FontStyle]
 * @param fontWeight a custom [FontWeight]
 * @param letterSpacing a custom [TextUnit]
 * @param textDecoration a custom [TextDecoration]
 * @param softWrap a custom flag to enable soft wrap
 * @param minLines defines what's the minimum number of lines is
 * @param onTextLayout a callback for when the layout is ready
 * @param style a custom [TextStyle]
 */
@Composable
fun PreferenceContentText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        text = text,
        modifier,
        color,
        fontSize,
        fontStyle,
        fontWeight,
        fontFamily,
        letterSpacing,
        textDecoration,
        TextAlign.End,
        lineHeight,
        TextOverflow.Ellipsis,
        softWrap,
        maxLines = LocalPreferenceSettings.current.maxLinesValue,
        minLines,
        onTextLayout,
        style
    )
}