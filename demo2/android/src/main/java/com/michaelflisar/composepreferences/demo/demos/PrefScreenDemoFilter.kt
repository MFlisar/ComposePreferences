package com.michaelflisar.composepreferences.demo.demos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.PreferenceInfo
import com.michaelflisar.composepreferences.core.PreferenceScreen
import com.michaelflisar.composepreferences.core.PreferenceSection
import com.michaelflisar.composepreferences.core.PreferenceSubScreen
import com.michaelflisar.composepreferences.core.classes.rememberPreferenceState
import com.michaelflisar.composepreferences.core.filter.DefaultPreferenceFilter
import com.michaelflisar.composepreferences.core.filter.rememberDefaultPreferenceFilter
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.demo.classes.DemoPrefs
import com.michaelflisar.kotpreferences.core.initialisation.SettingSetup
import com.michaelflisar.toolbox.components.MyCheckbox
import com.michaelflisar.toolbox.components.MyDropdown

@Composable
fun PrefScreenDemoFilter() {

    val settings = DemoPrefs.preferenceSettings()
    val filterModes = listOf(
        DefaultPreferenceFilter.Mode.ContainsText,
        DefaultPreferenceFilter.Mode.AllWords(false),
        DefaultPreferenceFilter.Mode.AnyWord(false)
    )
    val filter = rememberDefaultPreferenceFilter(
        mode = filterModes[1],
        highlightSpan = SpanStyle(color = Color.Red),
        flattenResult = true
    )

    val state = rememberPreferenceState()

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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

        MyCheckbox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            title = "Flat Results?",
            checked = filter.flattenResult
        )
        MyDropdown<DefaultPreferenceFilter.Mode>(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            items = filterModes,
            mapper = { item, dropdown -> item.javaClass.simpleName },
            selected = filter.mode,
            title = "Filter Mode"
        )

        PreferenceScreen(
            modifier = Modifier.weight(1f),
            settings = settings,
            filter = filter,
            state = state
        ) {
            // Empty nur anzeigen, falls durch Filtern leer!
            if (state.countCurrentLevel() > 0) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (state.countVisible() == 0) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Empty",
                            textAlign = TextAlign.Center
                        )
                    } else {
                        if (filter.isActive() && filter.flattenResult.value) {
                            Text(
                                "${state.countVisible()} / ${
                                    state.countAll(
                                        includeGroups = false,
                                        includeSections = false
                                    )
                                }"
                            )
                        } else {
                            Text("${state.countVisible()} / ${state.countCurrentLevel()}")
                        }

                    }
                }
            }
            PreferenceInfoExamples()
        }
    }
}

@Composable
private fun PreferenceScope.PreferenceInfoExamples() {
    PreferenceSubScreen(
        title = "Infos",
        subtitle = "Click to see some info preference examples",
        icon = { Icon(Icons.Outlined.Info, null) }
    ) {
        PreferenceSection(
            title = "Infos"
        ) {

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
                title = "Info 7",
                filterTags = listOf("Info 7")
            )

            PreferenceSubScreen(
                title = "Sub Infos",
                icon = { Icon(Icons.Outlined.Info, null) }
            ) {
                PreferenceSection(
                    title = "Sub Infos"
                ) {
                    PreferenceInfo(
                        title = "Sub Info 1",
                        subtitle = "Theme",
                        filterTags = listOf("style")
                    )
                    PreferenceInfo(title = "Sub Info 2")
                    PreferenceInfo(title = "Sub Info 3")
                    PreferenceInfo(title = "Sub Info 4")
                    PreferenceInfo(title = "Sub Info 5")
                    PreferenceInfo(title = "Sub Info 6")

                    PreferenceSubScreen(
                        title = "Sub Sub Infos",
                        icon = { Icon(Icons.Outlined.Info, null) }
                    ) {
                        PreferenceSection(
                            title = "Sub Sub Infos"
                        ) {
                            PreferenceInfo(title = "Sub Sub Info 1")
                            PreferenceInfo(title = "Sub Sub Info 2")
                            PreferenceInfo(title = "Sub Sub Info 3")
                        }
                    }
                }
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