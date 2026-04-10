|                                                    |                                                    |
|----------------------------------------------------|----------------------------------------------------|
| ![Screenshot](../screenshots/previews/number1.jpg) | ![Screenshot](../screenshots/previews/number2.jpg) |

This shows a simple number picker preference.

Check out the composable and it's documentation in the code snipplet below.

#### Example

<!-- snippet: demo-number -->
```kt
val number1 = dataStore.getInt("number1", 50)
    .collectAsState(initial = 50)
PreferenceNumber(
    value = number1.value,
    onValueChange = {
        scope.launch(DispatcherIO) {
            dataStore.update("number1", it)
        }
    },
    min = 0,
    max = 100,
    stepSize = 1,
    title = "Number 1",
    subtitle = "Select a number in the range [0, 100]",
    icon = { Icon(Icons.Default.Numbers, null) }
)
```
<!-- endSnippet -->

#### Composable

##### Data as `MutableState`

<!-- snippet: PreferenceNumber::constructor -->
```kt
/**
 * A number preference item - this item provides a number **picker** dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the [MutableState] of this item
 * @param min the minimum valid number
 * @param max the maximum valid number
 * @param stepSize the steps in which a number can be picked
 * @param formatter a formatter for a number
 */
@Composable
fun <T : Number> PreferenceScope.PreferenceNumber(
    style: PreferenceNumber.Style = PreferenceNumber.Style.Picker,
    // Special
    value: MutableState<T>,
    min: T,
    max: T,
    stepSize: T,
    formatter: @Composable (value: T) -> String = { it.toString() },
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceNumberDefaults.itemSetup(style),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceNumberDefaults.dialog(
            dialogState,
            value.value,
            { value.value = it },
            min,
            max,
            stepSize,
            formatter,
            title,
            icon
        )
    }
)
```
<!-- endSnippet -->

##### Data as `value` + `onValueChange`

<!-- snippet: PreferenceNumber::constructor2 -->
```kt
/**
 * A number preference item - this item provides a number **picker** dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the value of this item
 * @param onValueChange the value changed callback of this item
 * @param min the minimum valid number
 * @param max the maximum valid number
 * @param stepSize the steps in which a number can be picked
 * @param formatter a formatter for a number
 */
@Composable
fun <T : Number> PreferenceScope.PreferenceNumber(
    style: PreferenceNumber.Style = PreferenceNumber.Style.Picker,
    // Special
    value: T,
    onValueChange: (value: T) -> Unit,
    min: T,
    max: T,
    stepSize: T,
    formatter: @Composable (value: T) -> String = { it.toString() },
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceNumberDefaults.itemSetup(style),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceNumberDefaults.dialog(
            dialogState,
            value,
            onValueChange,
            min,
            max,
            stepSize,
            formatter,
            title,
            icon
        )
    }
)
```
<!-- endSnippet -->

#### Screenshots

|                                                         |                                                        |
|---------------------------------------------------------|--------------------------------------------------------|
| ![Screenshot](../screenshots/number/number-default.jpg) | ![Screenshot](../screenshots/number/number-modern.jpg) |
| ![Screenshot](../screenshots/number/number-dialog.jpg)  |                                                        |
