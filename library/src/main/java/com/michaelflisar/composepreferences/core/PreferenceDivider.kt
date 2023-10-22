package com.michaelflisar.composepreferences.core

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.composables.BasePreferenceContainer
import com.michaelflisar.composepreferences.core.composables.PreviewPreference
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

@Composable
fun PreferenceScope.PreferenceDivider(
    // Special
    // Base Preference
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled
) {
    BasePreferenceContainer(
        modifier = Modifier,
        enabled = enabled,
        visible = visible,
        onClick = null,
        preferenceStyle = LocalPreferenceSettings.current.itemStyle
    ) { modifier ->
        Divider(modifier = modifier)
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun Preview() {
    PreviewPreference {
        PreferenceDivider()
    }
}