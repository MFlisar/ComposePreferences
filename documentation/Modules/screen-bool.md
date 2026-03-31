|                                                  |                                                  |
|--------------------------------------------------|--------------------------------------------------|
| ![Screenshot](../screenshots/previews/bool1.jpg) | ![Screenshot](../screenshots/previews/bool2.jpg) |

This shows a simple boolean preference. It allows to toggle a boolean value.

Check out the composable and it's documentation in the code snipplet below.

#### Example

<!-- snippet: demo-bool -->
```kt
val bool1 = dataStore.getBool("bool1", true).collectAsState(initial = true)
PreferenceBool(
    style = PreferenceBool.Style.Switch,
    value = bool1.value,
    onValueChange = {
        scope.launch(DispatcherIO) {
            dataStore.update("bool1", it)
        }
    },
    title = "Bool 1",
    icon = { Icon(Icons.Default.Check, null) }
)
```
<!-- endSnippet -->

#### Composable

##### Data as `MutableState`

<!-- snippet: PreferenceBool::constructor -->
```kt
/**
 * A bool preference item - this item shows a checkbox/switch which reflects the preference state
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param style the [PreferenceBool.Style] of this item ([PreferenceBool.Style.Switch] or [PreferenceBool.Style.Checkbox])
 * @param value the [MutableState] of this item
 */
@Composable
fun PreferenceScope.PreferenceBool(
    style: PreferenceBool.Style = PreferenceBool.Style.Switch,
    // Special
    value: MutableState<Boolean>,
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceBoolDefaults.itemSetup(),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList()
)
```
<!-- endSnippet -->

##### Data as `value` + `onValueChange`

<!-- snippet: PreferenceBool::constructor2 -->
```kt
/**
 * A bool preference item - this item shows a checkbox/switch which reflects the preference state
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param style the [PreferenceBool.Style] of this item ([PreferenceBool.Style.Switch] or [PreferenceBool.Style.Checkbox])
 * @param value the value of this item
 * @param onValueChange the value changed callback of this item
 */
@Composable
fun PreferenceScope.PreferenceBool(
    style: PreferenceBool.Style = PreferenceBool.Style.Switch,
    // Special
    value: Boolean,
    onValueChange: (selected: Boolean) -> Unit,
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceBoolDefaults.itemSetup(),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList()
)
```
<!-- endSnippet -->

#### Screenshots

|                                                     |                                                    |
|-----------------------------------------------------|----------------------------------------------------|
| ![Screenshot](../screenshots/bool/bool-default.jpg) | ![Screenshot](../screenshots/bool/bool-modern.jpg) |
