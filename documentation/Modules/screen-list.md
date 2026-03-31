|                                                  |                                                  |
|--------------------------------------------------|--------------------------------------------------|
| ![Screenshot](../screenshots/previews/list1.jpg) | ![Screenshot](../screenshots/previews/list2.jpg) |

This shows a simple list preference. It allows to select one or multiple items from a list.

Check out the composable and it's documentation in the code snipplet below.

#### Example

##### Single Selection

<!-- snippet: demo-list -->
```kt
val list1 = dataStore.getInt("list1", 0)
    .collectAsState(initial = 0)
PreferenceList(
    style = PreferenceList.Style.Dialog(),
    value = list1.value,
    onValueChange = {
        scope.launch(DispatcherIO) {
            dataStore.update("list1", it)
        }
    },
    items = list,
    itemTextProvider = { icons[it].name },
    title = "List 1 (Dialog)",
    icon = { Icon(Icons.AutoMirrored.Filled.List, null) }
)
```
<!-- endSnippet -->

##### Multi Selection

<!-- snippet: demo-list2 -->
```kt
val multiList1 = dataStore.getIntList("multiList1", emptyList())
    .collectAsState(initial = emptyList())
PreferenceListMulti(
    value = multiList1.value,
    onValueChange = {
        scope.launch(DispatcherIO) {
            dataStore.update("multiList1", it)
        }
    },
    items = list,
    itemTextProvider = { icons[it].name },
    itemIconProvider = { Icon(icons[it], null) },
    title = "Multi List 1",
    subtitle = "This preference allows you to select multiple items from a list",
    icon = { Icon(Icons.Default.Checklist, null) }
)
```
<!-- endSnippet -->

#### Composable - Single Selection List

##### Data as `MutableState`

<!-- snippet: PreferenceList::constructor -->
```kt
/**
 * A list preference item - this item provides a list dialog or a dropdown to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param style the [PreferenceList.Style] of this item ([PreferenceList.Style.Dialog], [PreferenceList.Style.Spinner] or [PreferenceList.Style.SegmentedButtons])
 * @param value the [MutableState] of this item
 * @param items the list of items that this preference can select from
 * @param itemTextProvider a converter to get the text of an item
 * @param itemIconProvider a converter to provide an icon for an item
 * @param filter a lambda for filtering the list - if it is null, filtering is disabled (works with [PreferenceList.Style.Dialog] only!)
 */
@Composable
fun <T> PreferenceScope.PreferenceList(
    style: PreferenceList.Style = PreferenceList.Style.Dialog(),
    // listStyle
    value: MutableState<T>,
    items: List<T>,
    itemTextProvider: @Composable (item: T) -> String = { it.toString() },
    itemIconProvider: (@Composable (item: T) -> Unit)? = null,
    filter: ((filter: String, item: T) -> Boolean)? = null,
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceListDefaults.itemSetup(style),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceListDefaults.dialog(
            style,
            dialogState,
            value.value,
            { value.value = it },
            items,
            itemTextProvider,
            itemIconProvider,
            title,
            icon,
            filter?.let {
                DialogList.Filter(
                    filter = it,
                    keepSelectionForInvisibleItems = false
                )
            }
        )
    }
)
```
<!-- endSnippet -->

##### Data as `value` + `onValueChange`

<!-- snippet: PreferenceList::constructor2 -->
```kt
/**
 * A list preference item - this item provides a list dialog or a dropdown to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param style the [PreferenceList.Style] of this item ([PreferenceList.Style.Dialog] or [PreferenceList.Style.Spinner])
 * @param value the value of this item
 * @param onValueChange the value changed callback of this item
 * @param items the list of items that this preference can select from
 * @param itemTextProvider a converter to get the text of an item
 * @param itemIconProvider a converter to provide an icon for an item
 */
@Composable
fun <T> PreferenceScope.PreferenceList(
    style: PreferenceList.Style = PreferenceList.Style.Dialog(),
    // Special
    value: T,
    onValueChange: (value: T) -> Unit,
    items: List<T>,
    itemTextProvider: @Composable (item: T) -> String = { it.toString() },
    itemIconProvider: (@Composable (item: T) -> Unit)? = null,
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceListDefaults.itemSetup(style),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceListDefaults.dialog(
            style,
            dialogState,
            value,
            onValueChange,
            items,
            itemTextProvider,
            itemIconProvider,
            title,
            icon
        )
    }
)
```
<!-- endSnippet -->

#### Composable - Multi Selection List

##### Data as `MutableState`

<!-- snippet: PreferenceListMulti::constructor -->
```kt
/**
 * A list preference item - this item provides a list dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the [MutableState] of this item
 * @param items the list of items that this preference can select from
 * @param itemTextProvider a converter to get the text of an item
 * @param itemIconProvider a converter to provide an icon for an item
 */
@Composable
fun <T> PreferenceScope.PreferenceListMulti(
    // listStyle
    value: MutableState<List<T>>,
    items: List<T>,
    itemTextProvider: @Composable (item: T) -> String = { it.toString() },
    itemIconProvider: (@Composable (item: T) -> Unit)? = null,
    formatter: @Composable (selected: List<T>) -> String = { selected ->
        selected.map {
            itemTextProvider(it)
        }.joinToString(";")
    },
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceMultiListDefaults.itemSetup(),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceMultiListDefaults.dialog(dialogState, value.value, { value.value = it }, items, itemTextProvider, itemIconProvider, title, icon)
    }
)
```
<!-- endSnippet -->

##### Data as `value` + `onValueChange`

<!-- snippet: PreferenceListMulti::constructor2 -->
```kt
/**
 * A list preference item - this item provides a list dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the value of this item
 * @param onValueChange the value changed callback of this item
 * @param items the list of items that this preference can select from
 * @param itemTextProvider a converter to get the text of an item
 * @param itemIconProvider a converter to provide an icon for an item
 */
@Composable
fun <T> PreferenceScope.PreferenceListMulti(
    // Special
    value: List<T>,
    onValueChange: (value: List<T>) -> Unit,
    items: List<T>,
    itemTextProvider: @Composable (item: T) -> String = { it.toString() },
    itemIconProvider: (@Composable (item: T) -> Unit)? = null,
    formatter: @Composable (selected: List<T>) -> String = { selected ->
        selected.map {
            itemTextProvider(it)
        }.joinToString(";")
    },
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceMultiListDefaults.itemSetup(),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceMultiListDefaults.dialog(dialogState, value, onValueChange, items, itemTextProvider, itemIconProvider, title, icon)
    }
)
```
<!-- endSnippet -->

#### Screenshots

|                                                      |                                                     |
|------------------------------------------------------|-----------------------------------------------------|
| ![Screenshot](../screenshots/list/list-default.jpg)  | ![Screenshot](../screenshots/list/list-modern.jpg)  |
| ![Screenshot](../screenshots/list/list-dialog.jpg)   | ![Screenshot](../screenshots/list/list-dialog2.jpg) |
| ![Screenshot](../screenshots/list/list-dropdown.jpg) |                                                     |
