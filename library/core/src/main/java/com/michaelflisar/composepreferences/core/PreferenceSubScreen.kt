package com.michaelflisar.composepreferences.core

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetupDefaults
import com.michaelflisar.composepreferences.core.composables.PreviewPreference
import com.michaelflisar.composepreferences.core.hierarchy.LocalIndex
import com.michaelflisar.composepreferences.core.hierarchy.LocalOpenedGroups
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceGroupItem
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

/**
 * A sub screen container preference item - use this to wrap preference items inside a subscreen that will only show its content when it is opened (clicked)
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param onLongClick a optional long click listener for this item
 * @param ignoreMinItemHeight if true, this item will wrap its content and ignore the minimum item height
 * @param alignment the content alignment of this item
 */
@Composable
fun PreferenceScope.PreferenceSubScreen(
    // Special
    // Base Preference
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle,
    content: @Composable PreferenceScope.() -> Unit
) {
    val localIndex = LocalIndex.current
    val index = remember {
        mutableIntStateOf(localIndex.index)
    }
    val openedGroups = LocalOpenedGroups.current
    val settings = LocalPreferenceSettings.current

    //println("openedGroups: defined index: $index")
    PreferenceGroupItem(
        item = {
            BasePreference(
                setup = PreferenceItemSetup(
                    group = true,
                    trailingContentSize = PreferenceItemSetupDefaults.trailingContentSize(0.dp)
                ),
                enabled = enabled,
                visible = visible,
                title = {
                    title()
                },
                subtitle = subtitle,
                icon = icon,
                preferenceStyle = preferenceStyle,
                onClick = {
                    //println("openedGroups: add index: ${index.value} | localIndex = $localIndex")
                    openedGroups.add(index.value)
                },
                content = if (settings.subScreenEndIndicator == null) {
                    null
                } else {
                    {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        content()
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun Preview() {
    PreviewPreference {
        PreferenceSubScreen(
            icon = { Icon(Icons.Default.Info, null) },
            title = { Text(text = "Sub Screen Preference") },
            subtitle = { Text(text = "This is a description") }
        ) {

        }
    }
}