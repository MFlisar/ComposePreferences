package com.michaelflisar.composepreferences.demo.custom

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.NameNotFoundException
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.list.DialogList
import com.michaelflisar.composedialogs.dialogs.list.composables.DialogListContent
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceContentText
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetupDefaults
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun PreferenceScope.SelectAppPreference(
    // Special
    data: MutableState<AppItem>,
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    filterTags: List<String> = emptyList()
) {
    SelectAppPreference(
        value = data.value,
        onValueChange = { data.value = it },
        title = title,
        enabled = enabled,
        visible = visible,
        subtitle = subtitle,
        icon = icon,
        itemStyle = itemStyle,
        filterTags = filterTags
    )
}

@Composable
fun PreferenceScope.SelectAppPreference(
    // Special
    value: AppItem,
    onValueChange: (value: AppItem) -> Unit,
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    filterTags: List<String> = emptyList()
) {
    val showDialog = rememberDialogState()
    if (showDialog.showing) {
        val context = LocalContext.current
        DialogList(
            state = showDialog,
            title = { Text("Select App Dialog") },
            loadingIndicator = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Loading Apps...")
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator()
                }
            },
            itemsLoader = { SelectAppPreference.loadApps(context) },
            // AppItem is parcelable so autoSaver can handle it!
            itemSaver = autoSaver(),
            itemIdProvider = SelectAppPreference.ItemIdProvider,
            itemContents = SelectAppPreference.ItemContents,
            selectionMode = DialogList.SelectionMode.SingleClickAndClose {
                onValueChange(it)
            },
            icon = icon
        ) {
            // actually not used because of the selectionMode!!!
            if (it.isPositiveButton) {
                onValueChange(value)
            }
        }
    }
    val itemSetup = PreferenceItemSetup(
        trailingContentSize = PreferenceItemSetupDefaults.trailingContentSize(0.dp)
    )

    BasePreference(
        itemSetup = itemSetup,
        enabled = enabled,
        visible = visible,
        title = title,
        subtitle = subtitle,
        icon = icon,
        itemStyle = itemStyle,
        filterTags = filterTags,
        onClick = {
            showDialog.show()
        }
    ) {
        Row(
            modifier = Modifier.align(Alignment.End),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = rememberDrawablePainter(value.icon(LocalContext.current)),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            PreferenceContentText(value.label, itemSetup)
        }
    }
}

object SelectAppPreference {

    internal val ItemContents = object : DialogList.ItemContents<AppItem.ResolveInfo> {

        override val content: @Composable() (ColumnScope.(item: AppItem.ResolveInfo) -> Unit)
            get() = {
                DialogListContent(it.label, "ID: ${it.id}")
            }

        override val iconContent: @Composable() ((item: AppItem.ResolveInfo) -> Unit)?
            get() = {
                Image(
                    painter = rememberDrawablePainter(it.icon(LocalContext.current)),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }

        override val trailingContent: @Composable() (ColumnScope.(item: AppItem.ResolveInfo) -> Unit)?
            get() = null
    }

    internal val ItemIdProvider = { item: AppItem.ResolveInfo -> item.id }

    internal suspend fun loadApps(context: Context): List<AppItem.ResolveInfo> {
        return withContext(Dispatchers.IO) {
            val items = ArrayList<AppItem.ResolveInfo>()
            val pm = context.packageManager
            val intent = Intent(Intent.ACTION_MAIN, null).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
            val resolveInfos = pm.queryIntentActivities(intent, 0)
            var id = 1
            for (info in resolveInfos) {
                val text = info.loadLabel(pm)?.toString() ?: ""
                //val icon = info.loadIcon(context.packageManager)
                items.add(AppItem.ResolveInfo(id, info, text))
                id++
            }
            items.sortWith { o1, o2 -> o1.label.compareTo(o2.label, true) }
            items
        }
    }

    suspend fun loadSingleApp(context: Context, packageName: String): AppItem {
        return withContext(Dispatchers.IO) {
            val pm = context.packageManager
            try {
                val applicationInfo = pm.getApplicationInfo(packageName, 0)
                val text = applicationInfo.loadLabel(pm).toString()
                AppItem.ApplicationInfo(applicationInfo, text)
            } catch (e: NameNotFoundException) {
                AppItem.None
            }
        }
    }
}
