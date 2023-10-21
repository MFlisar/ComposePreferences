package com.michaelflisar.composepreferences.demo.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.michaelflisar.composepreferences.core.PreferenceInfo
import com.michaelflisar.composepreferences.core.PreferenceSectionHeader
import com.michaelflisar.composepreferences.core.PreferenceSubScreen
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

@Composable
fun PreferenceScope.DemoPreferenceGroup(
    type: String,
    details: String,
    icon: ImageVector,
    infos: String = "",
    content: @Composable PreferenceScope.() -> Unit
) {
    PreferenceSubScreen(
        title = { Text("$type Demos") },
        subtitle = { Text(details) },
        icon = { Icon(icon, null) }
    ) {
        if (infos.isNotEmpty()) {
            PreferenceInfo(
                title = {
                    Text("Infos", fontWeight = FontWeight.Bold)
                    Text(infos)
                }
            )
        }
        PreferenceSectionHeader(
            title = { Text("$type Demos") }
        )
        content()
    }
}