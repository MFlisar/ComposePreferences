package com.michaelflisar.composepreferences.demo.composables

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.classes.PreferenceState
import com.michaelflisar.democomposables.layout.DemoRow

@Composable
fun PreferenceNavigationAndInfo(state: PreferenceState) {
    DemoRow(
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        IconButton(
            onClick = {
                state.popLast()
            },
            enabled = state.currentLevel != 0
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowLeft, contentDescription = null)
        }
        Text(
            text = "Opened Hierarchy: ${state.opened.joinToString(" > ") { it.title }}",
            modifier = Modifier.weight(1f).horizontalScroll(rememberScrollState())
        )
    }
}