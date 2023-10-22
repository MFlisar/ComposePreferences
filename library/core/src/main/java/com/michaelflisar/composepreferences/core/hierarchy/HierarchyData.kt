package com.michaelflisar.composepreferences.core.hierarchy

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HierarchyData(
    val parents: List<Int>,
    val index: Int
) : Parcelable