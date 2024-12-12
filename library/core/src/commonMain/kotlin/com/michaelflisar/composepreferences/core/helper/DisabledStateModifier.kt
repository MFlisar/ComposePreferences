package com.michaelflisar.composepreferences.core.helper

//import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings

// simply copied from here: https://stackoverflow.com/a/76244926/1439522
private class GrayScaleModifier : DrawModifier {
    override fun ContentDrawScope.draw() {
        val saturationMatrix = ColorMatrix().apply { setToSaturation(0f) }

        val saturationFilter = ColorFilter.colorMatrix(saturationMatrix)
        val paint = Paint().apply {
            colorFilter = saturationFilter
        }
        drawIntoCanvas {
            it.saveLayer(Rect(0f, 0f, size.width, size.height), paint)
            drawContent()
            it.restore()
        }
    }
}

private fun Modifier.grayScale() = this.then(GrayScaleModifier())

internal fun Modifier.disableState(stateEnabled: State<Boolean>) = composed {
    this.then(
        if (stateEnabled.value)
            Modifier
        else {
            val disabledStateAlpha = LocalPreferenceSettings.current.disabledStateAlpha
            val disabledStateGrayscale = LocalPreferenceSettings.current.disabledStateGrayscale
            Modifier
                //.pointerInteropFilter { true }
                .graphicsLayer {
                    alpha = if (stateEnabled.value) 1f else disabledStateAlpha
                }
                .then(if (stateEnabled.value || !disabledStateGrayscale) Modifier else Modifier.grayScale())
        }
    )
}