package com.michaelflisar.composepreferences.demo.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.filter.DefaultPreferenceFilter
import com.michaelflisar.democomposables.DemoCheckbox
import com.michaelflisar.democomposables.DemoSpinner
import com.michaelflisar.democomposables.layout.DemoRegion
import com.michaelflisar.democomposables.layout.DemoRow

@Composable
fun PreferenceSettings(
    filter: DefaultPreferenceFilter,
    filterModes: List<DefaultPreferenceFilter.Mode>
) {
    DemoRegion("Settings")
    ElevatedCard {
        DemoRow(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {

            /* --8<-- [start: filter-flat] */
            DemoCheckbox(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                title = "Flat Results?",
                checked = filter.flattenResult
            )
            /* --8<-- [end: filter-flat] */
            DemoSpinner<DefaultPreferenceFilter.Mode>(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                items = filterModes,
                mapper = { item -> item.toString() },
                selected = filter.mode,
                label = "Filter Mode"
            )
        }
    }
}