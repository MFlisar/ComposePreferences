|                                                    |
|----------------------------------------------------|
| ![Screenshot](../screenshots/previews/button1.jpg) |

This shows a simple button preference. It allows you to handle a click action.

Check out the composable and it's documentation in the code snipplet below.

#### Example

<!-- snippet: demo-button -->
```kt
PreferenceButton(
    title = "Button 1",
    subtitle = "Clicking this button will increase counter 1",
    icon = { Icon(Icons.Default.AdsClick, null) },
    onClick = {
        showInfo("Button 1 clicked!")
        counter1++
    }
)
```
<!-- endSnippet -->

#### Composable

<!-- snippet: PreferenceButton::constructor -->
```kt
/**
 * A button preference item - this item simply executes an action on click
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param onClick the click callback of this item
 */
@Composable
fun PreferenceScope.PreferenceButton(
    // Special
    onClick: (() -> Unit),
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList()
)
```
<!-- endSnippet -->

#### Screenshots

|                                                         |                                                        |
|---------------------------------------------------------|--------------------------------------------------------|
| ![Screenshot](../screenshots/button/button-default.jpg) | ![Screenshot](../screenshots/button/button-modern.jpg) |
