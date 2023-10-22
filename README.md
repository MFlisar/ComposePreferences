### About

[![Release](https://jitpack.io/v/MFlisar/ComposePreferences.svg)](https://jitpack.io/#MFlisar/ComposePreferences)
![License](https://img.shields.io/github/license/MFlisar/ComposePreferences)

This library offers you simple preference screens for compose.

Made for **Compose M3**.

### Dependencies

| Dependency | Version | Infos |
|:-|-:|:-:|
| [Compose BOM](https://developer.android.com/jetpack/compose/bom/bom) | `2023.10.00` | [Mapping](https://developer.android.com/jetpack/compose/bom/bom-mapping) |
| Material3 | `1.1.2` | |

### Optional Dependencies

* Dialog based modules do depend on my [ComposeDialogs](https://github.com/MFlisar/ComposeDialogs) library
* Extensions do depend on the appearant library...

| Modules | Dependency | Version |
|:-|:-|-:|
| `screen-input`, `screen-color`,`screen-date`,`screen-time`,`screen-list` | [ComposeDialogs](https://github.com/MFlisar/ComposeDialogs) | 1.0.3 |
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
...
```

### Screenshots

| [![Demo](screenshots/previews/info1.jpg?raw=true)](https://github.com/MFlisar/ComposePreferences#existing-preferences) | ![Demo](screenshots/previews/info1.jpg?raw=true "Preview") |
|:-:|:-:|
| ![Demo](screenshots/previews/info1.jpg?raw=true "Preview") | ![Demo](screenshots/previews/info1.jpg?raw=true "Preview") |
| ![Demo](screenshots/previews/info1.jpg?raw=true "Preview") | ![Demo](screenshots/previews/info1.jpg?raw=true "Preview") |

### Existing preferences

| Preview | Constructor | Module |
| :- | :- | :- |
| ![Demo](screenshots/previews/info1.jpg?raw=true "Preview") | https://github.com/MFlisar/ComposePreferences/blob/3c0720aace8cf4bcc355d7dda2d2585c5ff31d1a/library/src/main/java/com/michaelflisar/composepreferences/core/PreferenceInfo.kt#L22-L34 | `core` |
| ![Demo](screenshots/previews/header1.jpg?raw=true "Preview") | https://github.com/MFlisar/ComposePreferences/blob/3c0720aace8cf4bcc355d7dda2d2585c5ff31d1a/library/src/main/java/com/michaelflisar/composepreferences/core/PreferenceSectionHeader.kt#L22-L31 | `core` |
| ![Demo](screenshots/previews/divider1.jpg?raw=true "Preview") | https://github.com/MFlisar/ComposePreferences/blob/3c0720aace8cf4bcc355d7dda2d2585c5ff31d1a/library/src/main/java/com/michaelflisar/composepreferences/core/PreferenceDivider.kt#L12-L17 | `core` |
