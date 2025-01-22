package com.michaelflisar.composepreferences.core.helper

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.michaelflisar.composepreferences.core.internal.PreferenceItemState

@Composable
internal fun AnimatedPreference(
    itemState: PreferenceItemState,
    animate: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    if (!animate) {
        if (itemState.visible.value) {
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
                animationSpec = tween()
            )
        }

        AnimatedVisibility(
            visibleState = itemState.visibleTransitionState,
            modifier = modifier,
            enter = enterTransition,
            exit = exitTransition
        ) {
            content()
        }
    }
}