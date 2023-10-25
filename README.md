### About

[![Release](https://jitpack.io/v/MFlisar/ComposePreferences.svg)](https://jitpack.io/#MFlisar/ComposePreferences)
![License](https://img.shields.io/github/license/MFlisar/ComposePreferences)

This library offers you simple preference screens for compose.

Made for **Compose M3**.

### Overview

![Overview](screenshots/overview.jpg?raw=true "Overview")#

### Dependencies

| Dependency | Version | Infos |
|:-|-:|:-:|
| [Compose BOM](https://developer.android.com/jetpack/compose/bom/bom) | `2023.10.00` | [Mapping](https://developer.android.com/jetpack/compose/bom/bom-mapping) |
| Material3 | `1.1.2` | |

### Other Dependencies

* Dialog based modules do depend on my [ComposeDialogs](https://github.com/MFlisar/ComposeDialogs) library
* Extensions do depend on the respective library...

| Modules | Dependency | Version |
|:-|:-|-:|
| `core` | no dependency |  |
| `screen-bool` | no dependency |  |
| `screen-button` | no dependency |  |
| `screen-input` | [ComposeDialogs](https://github.com/MFlisar/ComposeDialogs) | 1.0.3 |
| `screen-color` | [ComposeDialogs](https://github.com/MFlisar/ComposeDialogs) | 1.0.3 |
| `screen-date` | [ComposeDialogs](https://github.com/MFlisar/ComposeDialogs) | 1.0.3 |
| `screen-time` | [ComposeDialogs](https://github.com/MFlisar/ComposeDialogs) | 1.0.3 |
| `screen-list` | [ComposeDialogs](https://github.com/MFlisar/ComposeDialogs) | 1.0.3 |
| `screen-number` | [ComposeDialogs](https://github.com/MFlisar/ComposeDialogs) | 1.0.3 |
| `extension-kotpreferences` | [KotPreferences](https://github.com/MFlisar/KotPreferences) | 0.3 |

### Gradle (via [JitPack.io](https://jitpack.io/))

1. add jitpack to your project's `build.gradle`:

```groovy
repositories {
    maven { url "https://jitpack.io" }
}
```

2. add the compile statement to your module's `build.gradle`:

```groovy
dependencies {

    val composePreferences = "<LATEST-VERSION>"

    // core module
    implementation("com.github.MFlisar.ComposePreferences:core:$composePreferences")
  
    // modules
    implementation("com.github.MFlisar.ComposePreferences:screen-bool:$composePreferences")
    implementation("com.github.MFlisar.ComposePreferences:screen-button:$composePreferences")
    implementation("com.github.MFlisar.ComposePreferences:screen-input:$composePreferences")
    implementation("com.github.MFlisar.ComposePreferences:screen-color:$composePreferences")
    implementation("com.github.MFlisar.ComposePreferences:screen-date:$composePreferences")
    implementation("com.github.MFlisar.ComposePreferences:screen-time:$composePreferences")
    implementation("com.github.MFlisar.ComposePreferences:screen-list:$composePreferences")
    implementation("com.github.MFlisar.ComposePreferences:screen-number:$composePreferences")
    
    // extensions for other libraries
    implementation("com.github.MFlisar.ComposePreferences:extension-kotpreferences:$composePreferences")
}
```

The latest release can be found [here](https://github.com/MFlisar/ComposePreferences/releases/latest)

### Example

It works as simple as following:

```kotlin
// Preferences must be wrapped in a screen
// => this allows to manage internal hierarchy and screen nesting and everything is managed automatically
// => this also enables internal scrolling
PreferenceScreen(
    // optional parameters to customise this screen
    settings =  PreferenceSettingsDefaults.settings(),
    scrollable = true
) {
    // Preferences at root level
    PreferenceInfo(
        title = {  Text("Info 1") }
    )
    PreferenceBool(
        style = PreferenceBool.Style.Switch,
        value = <value>,
        onValueChange = {
            // update value here
        },
        title = { Text("Bool") }
        )
        
    // Sub Preference - all nested preferences will show if you click the sub preference (and all preferences from other levels will be hidden automatically)
    // + back press + state saving will be handled automatically
    PreferenceSubScreen(
        title = { Text("Menu") }
    ) {
        // sub preferences must be placed here
        // you can even nest another PreferenceSubScreen here - any nesting depth is supported!
    }
    
    // IMPORTANT:
    // don't place any non preference composables here, they won't be correctly shown/hidden and managed by the preference screen because they don't hold any hierarchical data!
    // also gray out and enabled states won't work
    // if you want to place some custom content wrap it inside a `BasePreference` (if you want the default title/subtitle/icon/content layout) 
    // or inside a `BasePreferenceContainer` if you want to place plain content
    
    // Custom 1 - default button inside the content area wrapped as preference => this will work correctly even if used in sub preferences and it will automatically support enabled/disabling
    BasePreference(
        title = { Text("A custom preference") },
        subtitle = { Text("Showing a button") },
        icon = { Icon(Icons.Default.Android, null) }
    ) {
        Button(onClick = {
            // ...
        }) {
            Icon(Icons.Default.Android, null)
        }
    }
    
    // Custom 2 - completely free content => this will also work correctly even if used in sub preferences and it will automatically support enabled/disabling
    // but it allows you to wrap ANY composable inside it
    BasePreferenceContainer(
        modifier = Modifier.padding(16.dp),
        preferenceStyle = PreferenceStyleDefaults.item()
    ) { modifier ->
        Button(
            onClick = {
                // ...
        }) {
            Text("Button")
        }
    }
}
```

###  Demo

A full [demo](demo/src/main/java/com/michaelflisar/compospreferences/demo/DemoActivity.kt) is included inside the *demo module*, it shows nearly every usage with working examples.

### Screenshots

| ![Demo](screenshots/light/root.jpg?raw=true "Demo") | ![Demo](screenshots/light/infos.jpg?raw=true "Demo") | ![Demo](screenshots/light/bool.jpg?raw=true "Demo") |
|:-:|:-:|:-:|
| ![Demo](screenshots/light/button.jpg?raw=true "Demo") | ![Demo](screenshots/light/color.jpg?raw=true "Demo") | ![Demo](screenshots/light/date.jpg?raw=true "Demo") |
| ![Demo](screenshots/light/input.jpg?raw=true "Demo") | ![Demo](screenshots/light/list.jpg?raw=true "Demo") | ![Demo](screenshots/light/number.jpg?raw=true "Demo") |
| ![Demo](screenshots/light/time.jpg?raw=true "Demo") | | |

### Existing preferences

##### Info Preference

| Preview | Module |
| :- | :- |
| ![Preview](screenshots/previews/info1.jpg?raw=true "Preview") | `core` |

https://github.com/MFlisar/ComposePreferences/blob/880e28fdb71bb0c56840758ca79802e96e2a7e5b/library/core/src/main/java/com/michaelflisar/composepreferences/core/PreferenceInfo.kt#L33-L45

##### Divider Preference

| Preview | Module |
| :- | :- |
| ![Preview](screenshots/previews/divider1.jpg?raw=true "Preview") | `core` |

https://github.com/MFlisar/ComposePreferences/blob/880e28fdb71bb0c56840758ca79802e96e2a7e5b/library/core/src/main/java/com/michaelflisar/composepreferences/core/PreferenceDivider.kt#L27-L32

##### Section Header Preference

| Preview | Module |
| :- | :- |
| ![Preview](screenshots/previews/header1.jpg?raw=true "Preview") | `core` |

https://github.com/MFlisar/ComposePreferences/blob/880e28fdb71bb0c56840758ca79802e96e2a7e5b/library/core/src/main/java/com/michaelflisar/composepreferences/core/PreferenceSectionHeader.kt#L30-L39

##### Bool Preference

| Preview | | Module |
| :- | :- | :- |
| ![Preview](screenshots/previews/bool1.jpg?raw=true "Preview") | ![Preview](screenshots/previews/bool2.jpg?raw=true "Preview") | `bool` |

https://github.com/MFlisar/ComposePreferences/blob/880e28fdb71bb0c56840758ca79802e96e2a7e5b/library/modules/screen/bool/src/main/java/com/michaelflisar/composepreferences/screen/bool/PreferenceBool.kt#L76-L88

##### Button Preference

| Preview | Module |
| :- | :- |
| ![Preview](screenshots/previews/button1.jpg?raw=true "Preview") | `button` |

https://github.com/MFlisar/ComposePreferences/blob/880e28fdb71bb0c56840758ca79802e96e2a7e5b/library/modules/screen/button/src/main/java/com/michaelflisar/composepreferences/screen/button/PreferenceButton.kt#L28-L38

##### Color Preference

| Preview | | Module |
| :- | :- | :- |
| ![Preview](screenshots/previews/color1.jpg?raw=true "Preview") | ![Preview](screenshots/previews/color2.jpg?raw=true "Preview") | `color` |

https://github.com/MFlisar/ComposePreferences/blob/880e28fdb71bb0c56840758ca79802e96e2a7e5b/library/modules/screen/color/src/main/java/com/michaelflisar/composepreferences/screen/color/PreferenceColor.kt#L88-L100

##### Date Preference

| Preview | Module |
| :- | :- |
| ![Preview](screenshots/previews/date1.jpg?raw=true "Preview") | `date` |

https://github.com/MFlisar/ComposePreferences/blob/880e28fdb71bb0c56840758ca79802e96e2a7e5b/library/modules/screen/date/src/main/java/com/michaelflisar/composepreferences/screen/date/PreferenceDate.kt#L85-L100

##### Input Preference

| Preview | | Module |
| :- | :- | :- |
| ![Preview](screenshots/previews/input-text1.jpg?raw=true "Preview") | | `input` |
| ![Preview](screenshots/previews/input-number1.jpg?raw=true "Preview") | ![Preview](screenshots/previews/input-number2.jpg?raw=true "Preview") | `input` |

https://github.com/MFlisar/ComposePreferences/blob/880e28fdb71bb0c56840758ca79802e96e2a7e5b/library/modules/screen/input/src/main/java/com/michaelflisar/composepreferences/screen/input/PreferenceInputText.kt#L67-L78

https://github.com/MFlisar/ComposePreferences/blob/880e28fdb71bb0c56840758ca79802e96e2a7e5b/library/modules/screen/input/src/main/java/com/michaelflisar/composepreferences/screen/input/PreferenceInputNumber.kt#L70-L81

##### List Preference

| Preview | | Module |
| :- | :- | :- |
| ![Preview](screenshots/previews/list1.jpg?raw=true "Preview") | ![Preview](screenshots/previews/list2.jpg?raw=true "Preview") | `list` |
| ![Preview](screenshots/previews/list-multi1.jpg?raw=true "Preview") | | `list` |

https://github.com/MFlisar/ComposePreferences/blob/880e28fdb71bb0c56840758ca79802e96e2a7e5b/library/modules/screen/list/src/main/java/com/michaelflisar/composepreferences/screen/list/PreferenceList.kt#L99-L114

https://github.com/MFlisar/ComposePreferences/blob/880e28fdb71bb0c56840758ca79802e96e2a7e5b/library/modules/screen/list/src/main/java/com/michaelflisar/composepreferences/screen/list/PreferenceListMulti.kt#L85-L104

##### Number Preference

| Preview | | Module |
| :- | :- | :- |
| ![Preview](screenshots/previews/number1.jpg?raw=true "Preview") | ![Preview](screenshots/previews/number2.jpg?raw=true "Preview") | `number` |

https://github.com/MFlisar/ComposePreferences/blob/880e28fdb71bb0c56840758ca79802e96e2a7e5b/library/modules/screen/number/src/main/java/com/michaelflisar/composepreferences/screen/number/PreferenceNumber.kt#L95-L111

##### Time Preference

| Preview | Module |
| :- | :- |
| ![Preview](screenshots/previews/time1.jpg?raw=true "Preview") | `time` |

https://github.com/MFlisar/ComposePreferences/blob/880e28fdb71bb0c56840758ca79802e96e2a7e5b/library/modules/screen/time/src/main/java/com/michaelflisar/composepreferences/screen/time/PreferenceTime.kt#L83-L96