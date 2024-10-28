package com.michaelflisar.composepreferences.core.scopes

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Immutable

@LayoutScopeMarker
@Immutable
interface PreferenceRootScope : PreferenceScope
internal object PreferenceRootScopeInstance : PreferenceRootScope