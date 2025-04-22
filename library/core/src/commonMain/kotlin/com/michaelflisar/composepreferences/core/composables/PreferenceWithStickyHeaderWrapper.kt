package com.michaelflisar.composepreferences.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import com.michaelflisar.composepreferences.core.LocalPreferenceScrollState
import com.michaelflisar.composepreferences.core.scopes.PreferenceGroupScope
import com.michaelflisar.composepreferences.core.scopes.PreferenceGroupScopeInstance
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle

@Composable
internal fun PreferenceWithStickyHeaderWrapper(
    itemStyle: PreferenceItemStyle,
    stickyHeader: @Composable (PreferenceGroupScope.() -> Unit)? = null,
    content: @Composable PreferenceGroupScope.() -> Unit
) {
    var stickyHeaderSize by remember { mutableStateOf(IntOffset.Zero) }
    Box {
        Column(
            modifier = Modifier
                .offset { stickyHeaderSize }
        ) {
            PreferenceGroupScopeInstance.content()
            // to allow scrolling to the bottom
            if (stickyHeader != null) {
                Spacer(modifier = Modifier.height(with(LocalDensity.current) { stickyHeaderSize.y.toDp() }))
            }
        }
        if (stickyHeader != null) {
            val scrollState = LocalPreferenceScrollState.current.value
            Column(
                modifier = Modifier
                    .onSizeChanged {
                        stickyHeaderSize = IntOffset(0, it.height)
                    }
                    .offset { IntOffset(0, scrollState.value) }
                    // for "clipping" the content above when it is scrolled behind this sticky header
                    .background(itemStyle.colors.containerColor().value)
                    .zIndex(1f)
            ) {
                PreferenceGroupScopeInstance.stickyHeader()
            }
        }
    }
}