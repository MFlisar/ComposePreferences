package com.michaelflisar.composepreferences.demo.composables

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.michaelflisar.composepreferences.core.PreferenceInfo
import com.michaelflisar.composepreferences.core.PreferenceSubScreen
import com.michaelflisar.composepreferences.core.scopes.PreferenceGroupScope
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.core.styles.PreferenceStyleDefaults

@Composable
fun PreferenceScope.DemoPreferenceGroup(
    type: String,
    details: String,
    icon: ImageVector,
    infos: String = "",
    content: @Composable PreferenceGroupScope.() -> Unit
) {
    PreferenceSubScreen(
        title = "$type Demos",
        subtitle = details,
        icon = { Icon(icon, null) }
    ) {
        if (infos.isNotEmpty()) {
            PreferenceInfo(
                title = "Infos",
                subtitle = infos
            )
        }
        PreferenceInfo(title = "$type Demos", itemStyle = PreferenceStyleDefaults.header())
        content()
    }
}