|                                                          |                                                         |
|----------------------------------------------------------|---------------------------------------------------------|
| ![Screenshot](../screenshots/previews/input-text1.jpg)   | ![Screenshot](../screenshots/previews/input-number1.jpg) |
| ![Screenshot](../screenshots/previews/input-number2.jpg) | |

This shows a simple input preference. It allows you to input a `string` or a `numeric` value.

Check out the composable and it's documentation in the code snipplet below.

#### Example

##### Text

<!-- snippet: demo-input -->
```kt
val input1 = dataStore.getString("input1", "Hello")
    .collectAsState(initial = "Hello")
PreferenceInputText(
    value = input1.value,
    onValueChange = {
        scope.launch(DispatcherIO) {
            dataStore.update("input1", it)
        }
    },
    title = "Input 1",
    subtitle = "String input example",
    icon = { Icon(Icons.AutoMirrored.Filled.TextSnippet, null) }
)
```
<!-- endSnippet -->

##### Numeric

<!-- snippet: demo-input2 -->
```kt
val input2 = dataStore.getInt("input2", 100)
    .collectAsState(initial = 100)
PreferenceInputNumber(
    value = input2.value,
    onValueChange = {
        scope.launch(DispatcherIO) {
            dataStore.update("input2", it)
        }
    },
    title = "Input 2",
    subtitle = "This input preference does only accept valid Int numbers",
    icon = { Icon(Icons.Default.Numbers, null) }
)
```
<!-- endSnippet -->

#### Composable Text

##### Data as `MutableState`

<!-- snippet: PreferenceInputText::constructor -->
```kt
/**
 * A text preference item - this item provides a text input dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the [MutableState] of this item
 * @param validator the [DialogInputValidator] of this item
 */
@Composable
fun PreferenceScope.PreferenceInputText(
    // Special
    value: MutableState<String>,
    validator: DialogInputValidator = rememberDialogInputValidator(),
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceInputTextDefaults.itemSetup(),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceInputTextDefaults.dialog(dialogState, value.value, { value.value = it }, validator, title, icon)
    }
)
```
<!-- endSnippet -->

##### Data as `value` + `onValueChange`

<!-- snippet: PreferenceInputText::constructor2 -->
```kt
/**
 * A text preference item - this item provides a text input dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the value of this item
 * @param onValueChange the value changed callback of this item
 * @param validator the [DialogInputValidator] of this item
 */
@Composable
fun PreferenceScope.PreferenceInputText(
    // Special
    value: String,
    onValueChange: (value: String) -> Unit,
    validator: DialogInputValidator = rememberDialogInputValidator(),
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceInputTextDefaults.itemSetup(),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceInputTextDefaults.dialog(dialogState, value, onValueChange, validator, title, icon)
    }
)
```
<!-- endSnippet -->

#### Composable Numeric

##### Data as `MutableState`

<!-- snippet: PreferenceInputNumber::constructor -->
```kt
/**
 * A number input preference item - this item provides a input dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the [MutableState] of this item
 * @param validator the [DialogInputValidator] of this item
 * @param formatter the formatter of this item
 */
@Composable
fun <T : Number> PreferenceScope.PreferenceInputNumber(
    // Special
    value: MutableState<T>,
    validator: DialogInputValidator = DialogInputNumber.rememberDefaultValidator(value.value),
    formatter: (value: T) -> String = { it.toString() },
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceInputNumberDefaults.itemSetup(),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceInputNumberDefaults.dialog(dialogState, value.value, { value.value = it }, validator, title, icon)
    }
)
```
<!-- endSnippet -->

##### Data as `value` + `onValueChange`

<!-- snippet: PreferenceInputNumber::constructor2 -->
```kt
/**
 * A number input preference item - this item provides a input dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the value of this item
 * @param onValueChange the value changed callback of this item
 * @param validator the [DialogInputValidator] of this item
 * @param formatter the formatter of this item
 */
@Composable
fun <T : Number> PreferenceScope.PreferenceInputNumber(
    // Special
    value: T,
    onValueChange: (value: T) -> Unit,
    validator: DialogInputValidator = DialogInputNumber.rememberDefaultValidator(value),
    formatter: (value: T) -> String = { it.toString() },
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceInputNumberDefaults.itemSetup(),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceInputNumberDefaults.dialog(dialogState, value, onValueChange, validator, title, icon)
    }
)
```
<!-- endSnippet -->

#### Screenshots

|                                                       |                                                      |
|-------------------------------------------------------|------------------------------------------------------|
| ![Screenshot](../screenshots/input/input-default.jpg) | ![Screenshot](../screenshots/input/input-modern.jpg) |
| ![Screenshot](../screenshots/input/input-dialog.jpg)  |                                                      |
