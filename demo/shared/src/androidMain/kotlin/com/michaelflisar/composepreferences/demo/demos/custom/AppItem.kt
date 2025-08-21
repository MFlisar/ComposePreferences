package com.michaelflisar.composepreferences.demo.demos.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

sealed class AppItem : Parcelable {

    abstract fun icon(context: Context): Drawable?
    abstract val label: String
    abstract val packageName: String

    @Parcelize
    class ResolveInfo(
        val id: Int,
        val resolveInfo: android.content.pm.ResolveInfo,
        override val label: String,
    ) : AppItem() {
        override fun icon(context: Context) = resolveInfo.loadIcon(context.packageManager)
        override val packageName: String
            get() = resolveInfo.activityInfo.packageName
    }

    @Parcelize
    data class ApplicationInfo(
        val applicationInfo: android.content.pm.ApplicationInfo,
        override val label: String,
    ) : AppItem() {
        override fun icon(context: Context) = applicationInfo.loadIcon(context.packageManager)
        override val packageName: String
            get() = applicationInfo.packageName
    }

    @Parcelize
    data object None : AppItem() {
        override fun icon(context: Context) = null
        @IgnoredOnParcel
        override val label = "NO APP SELECTED"
        @IgnoredOnParcel
        override val packageName = ""
    }
}

