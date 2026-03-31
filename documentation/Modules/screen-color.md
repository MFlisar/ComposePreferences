|                                                   |                                                   |
|---------------------------------------------------|---------------------------------------------------|
| ![Screenshot](../screenshots/previews/color1.jpg) | ![Screenshot](../screenshots/previews/color2.jpg) |

This shows a simple color picker preference.

Check out the composable and it's documentation in the code snipplet below.

#### Example

<!-- snippet: demo-color -->
```kt
val color1 = dataStore.getInt("color1", Color.Red.toArgb())
    .collectAsState(initial = Color.Red.toArgb())
PreferenceColor(
    value = Color(color1.value),
    onValueChange = {
        scope.launch(DispatcherIO) {
            dataStore.update("color1", it.toArgb())
        }
    },
    title = "Color 1",
    subtitle = "This preference does support ALPHA values",
    icon = { Icon(Icons.Default.ColorLens, null) }
)
```
<!-- endSnippet -->

#### Composable

##### Data as `MutableState`

<!-- snippet: PreferenceColor::constructor -->
```kt
/**
 * A color preference item - this item provides a color dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the [MutableState] of this item
 * @param alphaSupported if true, this preference does support alpha values (ARGB) otherwise it doesn't (RGB only)
 * @param directEditSupported if true, this preference does allow direct editing of rgba values (in the custom color page)
 */
@Composable
fun PreferenceScope.PreferenceColor(
    // Special
    value: MutableState<Color>,
    alphaSupported: Boolean = true,
    directEditSupported: Boolean = false,
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceColorDefaults.itemSetup(),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceColorDefaults.dialog(dialogState, value.value, { value.value = it }, alphaSupported, directEditSupported, title, icon)
    }
)
```
<!-- endSnippet -->

##### Data as `value` + `onValueChange`

<!-- snippet: PreferenceColor::constructor2 -->
```kt
/**
 * A color preference item - this item provides a color dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the color value of this item
 * @param onValueChange the value changed callback of this item
 * @param alphaSupported if true, this preference does support alpha values (ARGB) otherwise it doesn't (RGB only)
 * @param directEditSupported if true, this preference does allow direct editing of rgba values (in the custom color page)
 */
@Composable
fun PreferenceScope.PreferenceColor(
    // Special
    value: Color,
    onValueChange: (value: Color) -> Unit,
    alphaSupported: Boolean = true,
    directEditSupported: Boolean = false,
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceColorDefaults.itemSetup(),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceColorDefaults.dialog(dialogState, value, onValueChange, alphaSupported, directEditSupported,title, icon)
    }
)
```
<!-- endSnippet -->

#### Screenshots

|                                                       |                                                       |
|-------------------------------------------------------|-------------------------------------------------------|
| ![Screenshot](../screenshots/color/color-default.jpg) | ![Screenshot](../screenshots/color/color-modern.jpg)  |
| ![Screenshot](../screenshots/color/color-dialog.jpg)  | ![Screenshot](../screenshots/color/color-dialog2.jpg) |
