package com.michaelflisar.composepreferences.core.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.michaelflisar.composepreferences.core.PreferenceScreen
import com.michaelflisar.composepreferences.core.classes.PreferenceSettingsDefaults
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

/*
 helper function for previews that sets a theme and surface and disables animations (animations via LaunchedEffect do not work in preview!)
 */
@Composable
fun PreviewPreference(
    content: @Composable PreferenceScope.() -> Unit
) {
    val colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    MaterialTheme(
        colorScheme = colorScheme
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            PreferenceScreen(
                settings = PreferenceSettingsDefaults.settings(
                    animationSpec = null
                )
            ) {
                content()
            }
        }
    }
}