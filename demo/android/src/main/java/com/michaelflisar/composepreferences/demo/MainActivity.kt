package com.michaelflisar.composepreferences.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.PreferenceScreen
import com.michaelflisar.composepreferences.core.styles.ModernStyle
import com.michaelflisar.composepreferences.demo.classes.DemoDataStore
import com.michaelflisar.composepreferences.demo.classes.DemoPrefs
import com.michaelflisar.composepreferences.demo.classes.DemoStyle
import com.michaelflisar.composepreferences.demo.classes.LocalDataStore
import com.michaelflisar.composepreferences.demo.demos.PrefScreenCustomDemo
import com.michaelflisar.composepreferences.demo.demos.PrefScreenDemo
import com.michaelflisar.composepreferences.demo.demos.PrefScreenDemoFilter
import com.michaelflisar.composepreferences.demo.demos.PrefScreenDemoKotPreferences1
import com.michaelflisar.composepreferences.screen.bool.PreferenceBool
import com.michaelflisar.composepreferences.screen.list.PreferenceList
import com.michaelflisar.composepreferences.screen.number.PreferenceNumber
import com.michaelflisar.kmptemplate.composables.DemoCollapsibleRegion
import com.michaelflisar.kmptemplate.composables.DemoColumn
import com.michaelflisar.kmptemplate.composables.rememberDemoExpandedRegions
import com.michaelflisar.kotpreferences.compose.asMutableStateNotNull
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    },
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    },
                    content = { padding ->
                        Column(
                            modifier = Modifier
                                .padding(padding)
                                .fillMaxSize()
                        ) {
                            // not beautiful but a easy solution good enough for demo purposes
                            val context = LocalContext.current
                            CompositionLocalProvider(
                                LocalDataStore provides DemoDataStore(context)
                            ) {
                                // preload data store
                                val dataStore = LocalDataStore.current
                                LaunchedEffect(Unit) {
                                    dataStore.preload()
                                }

                                val page = rememberSaveable {
                                    mutableIntStateOf(0)
                                }

                                BackHandler(page.intValue != 0) {
                                    page.intValue = 0
                                }

                                Content(
                                    Modifier,
                                    page
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

// ----------------
// UI - Content
// ----------------

@Composable
private fun Content(
    modifier: Modifier,
    page: MutableState<Int>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        when (page.value) {
            0 -> Root(page, listOf("Demo", "Demo KotPreference", "Demo Custom", "Demo Filter"))
            1 -> PrefScreenDemo()
            2 -> PrefScreenDemoKotPreferences1()
            3 -> PrefScreenCustomDemo()
            4 -> PrefScreenDemoFilter()
        }
    }
}

@Composable
private fun Root(
    page: MutableState<Int>,
    buttons: List<String>
) {
    val regionState = rememberDemoExpandedRegions(listOf(0, 2))

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        // --------------------
        // App Settings for demos
        // --------------------

        DemoCollapsibleRegion(
            title = "Preference Settings",
            info = "Those settings are used for all preferences in this demo app!",
            regionId = 1,
            state = regionState
        ) {
            val settings = DemoPrefs.preferenceSettings(
                style = ModernStyle.create(
                    outerPadding = PaddingValues(0.dp)
                ),
                animationSpec = null
            )

            PreferenceScreen(
                scrollable = false,
                settings = settings
            ) {
                PreferenceList(
                    value = DemoPrefs.style.asMutableStateNotNull(),
                    title = "Style",
                    items = DemoStyle.entries.toList(),
                    icon = { Icon(Icons.Default.Style, null) },
                )
                PreferenceNumber(
                    value = DemoPrefs.disabledStateAlpha.asMutableStateNotNull(),
                    title = "Alpha",
                    subtitle = "Alpha value for disabled preferences",
                    icon = { Icon(Icons.Default.Settings, null) },
                    min = 0f,
                    max = 1f,
                    stepSize = 0.1f,
                    formatter = {
                        NumberFormat.getInstance(Locale.ENGLISH).apply {
                            maximumFractionDigits = 1
                            minimumFractionDigits = 1
                        }.format(it)
                    }
                )
                PreferenceBool(
                    style = PreferenceBool.Style.Switch,
                    value = DemoPrefs.disabledStateGrayscale.asMutableStateNotNull(),
                    title = "Grayscale",
                    subtitle = "Grayscale out disabled preferences?",
                    icon = { Icon(Icons.Default.Settings, null) }
                )
                PreferenceBool(
                    style = PreferenceBool.Style.Switch,
                    value = DemoPrefs.toggleBooleanOnItemClick.asMutableStateNotNull(),
                    title = "Bool Behaviour",
                    subtitle = "Toggle boolean preferences on item click (or on checkbox/switch click only)?",
                    icon = { Icon(Icons.Default.Settings, null) }
                )
                PreferenceNumber(
                    value = DemoPrefs.maxLinesValue.asMutableStateNotNull(),
                    title = "Lines",
                    subtitle = "Maximum number of lines for all preferences that show some text inside their content area.",
                    icon = { Icon(Icons.Default.Settings, null) },
                    min = 0,
                    max = 5,
                    stepSize = 1
                )
                PreferenceBool(
                    style = PreferenceBool.Style.Switch,
                    value = DemoPrefs.showSubScreenEndIndicator.asMutableStateNotNull(),
                    title = "Sub Screen Indicator",
                    subtitle = "Show an arrow on the right for sub screens?",
                    icon = { Icon(Icons.Default.Settings, null) }
                )
                PreferenceBool(
                    style = PreferenceBool.Style.Switch,
                    value = DemoPrefs.forceNoIconInset.asMutableStateNotNull(),
                    title = "No Icon Inset",
                    subtitle = "Force an inset for preferences without an icon, so that all preferences align beautifully on the left?",
                    icon = { Icon(Icons.Default.Settings, null) }
                )
            }
        }

        // --------------------
        // Demos
        // --------------------

        DemoCollapsibleRegion(title = "Demos", regionId = 2, state = regionState) {
            DemoColumn {
                MyInfoLine(
                    label = "INFORMATION",
                    info = "Only the first demo showcases all available preferences - the other demos just showcase the basic usage because everything else just works the same!",
                    showEqualSign = false
                )
                buttons.forEachIndexed { index, s ->
                    OutlinedButton(
                        onClick = {
                            page.value = index + 1
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(s)
                    }
                }
            }
        }

    }
}

@Composable
private fun MyInfoLine(
    label: String,
    info: String,
    modifier: Modifier = Modifier,
    showEqualSign: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(label, style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
        if (showEqualSign) {
            Text(
                "=",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        } else {
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(info, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
    }
}