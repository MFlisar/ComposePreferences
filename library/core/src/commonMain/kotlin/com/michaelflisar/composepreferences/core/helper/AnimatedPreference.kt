package com.michaelflisar.composepreferences.core.helper

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun AnimatedPreference(
    visible: Boolean,
    animate: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    if (!animate) {
        if (visible) {
            content()
        }
    } else {
        val enterTransition = remember {
            expandVertically(
                expandFrom = Alignment.Top,
                animationSpec = tween()
            ) + fadeIn(
                initialAlpha = 0.3f,
                animationSpec = tween()
            )
        }
        val exitTransition = remember {
            shrinkVertically(
                // Expand from the top.
                shrinkTowards = Alignment.Top,
                animationSpec = tween()
            ) + fadeOut(
                // Fade in with the initial alpha of 0.3f.
                animationSpec = tween()
            )
        }

        AnimatedVisibility(
            visible = visible,
            modifier = modifier,
            enter = enterTransition,
            exit = exitTransition
        ) {
            content()
        }
    }
}