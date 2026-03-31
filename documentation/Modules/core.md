This is the core module. It contains all the basic preference stuff like `styles`, `sections`, `groups`, `hierarchy management`, etc...

The general usage is already described in the usage region. In the following I shortly describe how the main composables work. This module also contains all classes that are needed for advanced usage - check out the advanced section for more details.

## PreferenceInfo

|                                              |                                                  |
|----------------------------------------------|--------------------------------------------------|
| ![Screenshot](../screenshots/previews/bool1.jpg) | ![Screenshot](../screenshots/previews/bool2.jpg) |

This shows a simple information preference.

<!-- snippet: PreferenceInfo::constructor -->
```kt
/**
 * A info preference item
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param onLongClick a optional long click listener for this item
 */
@Composable
fun PreferenceScope.PreferenceInfo(
    // Special
    onLongClick: (() -> Unit)? = null,
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceInfoDefaults.itemSetup(),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList()
)
```
<!-- endSnippet -->

## PreferenceDivider

This shows a simple divider preference.

<!-- snippet: PreferenceDivider::constructor -->
```kt
/**
 * A divider preference item
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 */
@Composable
fun PreferenceScope.PreferenceDivider(
    // Special
    // Base Preference
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    filterTags: List<String> = emptyList()
)
```
<!-- endSnippet -->

## PreferenceSection

|                                                     |                                                     |
|-----------------------------------------------------|-----------------------------------------------------|
| ![Screenshot](../screenshots/previews/section1.jpg) | ![Screenshot](../screenshots/previews/section2.jpg) |

This allows you to wrap a group of preferences in a section. Depending on the style this may also change the visual appearance of the preferences.

<!-- snippet: PreferenceSection::constructor -->
```kt
/**
 * A section preference item
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 */
@Composable
fun PreferenceGroupScope.PreferenceSection(
    // Special
    expandable: PreferenceSection.Expandable = PreferenceSection.Expandable.Disabled,
    // Base Preference
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    title: String? = null,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultSectionItemStyle,
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    content: @Composable PreferenceSectionScope.() -> Unit,
)
```
<!-- endSnippet -->

## PreferenceSubScreen

|                                                       |
|-------------------------------------------------------|
| ![Screenshot](../screenshots/previews/subscreen1.jpg) |

This allows you to wrap a group of preferences in a sub screen. This means, that all children will only be visible if the user clicks on the preference. This will then open the sub screen and show it's content.

<!-- snippet: PreferenceSubScreen::constructor -->
```kt
/**
 * A group preference item
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 */
@Composable
fun PreferenceScope.PreferenceSubScreen(
    // Special
    // Base Preference
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    title: String,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultGroupItemStyle,
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    content: @Composable PreferenceGroupScope.() -> Unit,
)
```
<!-- endSnippet -->
