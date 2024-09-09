package com.michaelflisar.composepreferences.demo.demos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.PreferenceInfo
import com.michaelflisar.composepreferences.core.PreferenceScreen
import com.michaelflisar.composepreferences.core.PreferenceSectionHeader
import com.michaelflisar.composepreferences.core.PreferenceSubScreen
import com.michaelflisar.composepreferences.core.classes.PreferenceFilter
import com.michaelflisar.composepreferences.core.classes.rememberPreferenceFilter
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope
import com.michaelflisar.composepreferences.demo.classes.DemoPrefs
import com.michaelflisar.kotpreferences.core.initialisation.SettingSetup

@Composable
fun PrefScreenDemoFilter() {

    val settings = DemoPrefs.preferenceSettings()
    val filter = rememberPreferenceFilter(
        mode = PreferenceFilter.Mode.AllWords,
        highlightSpan = SpanStyle(color = Color.Red)
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            value = filter.search.value,
            onValueChange = { filter.search.value = it },
            label = { Text("Search") },
            trailingIcon = if (filter.search.value.isNotEmpty()) {
                {
                    IconButton(onClick = { filter.search.value = "" }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "clear"
                        )
                    }
                }
            } else null
        )

        PreferenceScreen(
            settings = settings,
            filter = filter
        ) {
            PreferenceSectionHeader(
                title = { Text("Demos") }
            )
            PreferenceInfoExamples()
        }
    }
}

@Composable
private fun PreferenceScope.PreferenceInfoExamples() {
    PreferenceSubScreen(
        title = { Text("Infos") },
        subtitle = { Text("Click to see some info preference examples") },
        icon = { Icon(Icons.Outlined.Info, null) }
    ) {
        PreferenceSectionHeader(
            title = { Text("Infos") }
        )

        // either use the TEXT BASED OVERLOADS (those will add all texts to the filter tags list)
        // or provide a list of manually defined filter tags
        PreferenceInfo(title = "Info 1", subtitle = "Color")
        PreferenceInfo(
            title = "Info 2",
            subtitle = "Color2",
            filterTags = listOf("red", "green", "blue")
        )
        PreferenceInfo(title = "Info 3")
        PreferenceInfo(title = "Info 4")
        PreferenceInfo(title = "Info 5")
        PreferenceInfo(title = "Info 6")

        PreferenceInfo(
            title = { Text("Info 7") },
            filterTags = listOf("Info 7")
        )

        PreferenceSubScreen(
            title = { Text("Sub Infos") },
            icon = { Icon(Icons.Outlined.Info, null) }
        ) {
            PreferenceInfo(title = "Sub Info 1", subtitle = "Theme", filterTags = listOf("style"))
            PreferenceInfo(title = "Sub Info 2")
            PreferenceInfo(title = "Sub Info 3")
            PreferenceInfo(title = "Sub Info 4")
            PreferenceInfo(title = "Sub Info 5")
            PreferenceInfo(title = "Sub Info 6")

            PreferenceSubScreen(
                title = { Text("Sub Sub Infos") },
                icon = { Icon(Icons.Outlined.Info, null) }
            ) {
                PreferenceInfo(title = "Sub Sub Info 1")
                PreferenceInfo(title = "Sub Sub Info 2")
                PreferenceInfo(title = "Sub Sub Info 3")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Preview() {
    SettingSetup.init(LocalContext.current)
    MaterialTheme {
        Surface(
            modifier = Modifier
                .height(400.dp)
        ) {
            PrefScreenDemoFilter()
        }
    }
}