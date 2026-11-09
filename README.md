[![Maven Central](https://img.shields.io/maven-central/v/io.github.mflisar.composepreferences/core?color=blue)](https://central.sonatype.com/artifact/io.github.mflisar.composepreferences/core) ![API](https://img.shields.io/badge/api-24%2B-brightgreen.svg) ![Kotlin](https://img.shields.io/github/languages/top/MFlisar/ComposePreferences.svg?color=blueviolet) ![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin_Multiplatform-blue?label=Kotlin)
# ComposePreferences
![Platforms](https://img.shields.io/badge/PLATFORMS-black?style=for-the-badge)

This library offers you an easily extendible compose framework for preferences.

> [!NOTE]
> All features are splitted into separate modules, just include the modules you want to use!

> [!TIP]
> Try the interactive demo:
> 
> [![Open Interactive Demo](https://img.shields.io/badge/ComposePreferences_Demo-2962FF?style=for-the-badge&logo=googlechrome&logoColor=white)](https://mflisar.github.io/ComposePreferences/demo/)

# Table of Contents

- [Supported Platforms](#computer-supported-platforms)
- [Versions](#arrow_right-versions)
- [Setup](#wrench-setup)
- [Usage](#rocket-usage)
- [Demo](#sparkles-demo)
- [API](#books-api)
- [Other Libraries](#bulb-other-libraries)

# :computer: Supported Platforms

| Module | Notes |
|---|---|
| core | provides all the basic preference classes and composables |
| screen-bool | provides a checkbox/switch preference screen |
| screen-button | provides a button preference screen |
| screen-color | provides a color picker preference screen |
| screen-date | provides a date picker preference screen |
| screen-input | provides a input dialog preference screen |
| screen-list | provides a list dialog preference screen |
| screen-number | provides a number picker preference screen |
| screen-time | provides a time picker preference screen |
| kotpreferences | provides extensions to use KotPreferences with ComposePreferences |

# :arrow_right: Versions

| Dependency | Version |
|---|---|
| Kotlin | `2.4.21` |
| Jetbrains Compose | `1.12.2` |
| Jetbrains Compose Material3 | `1.9.0` |

> :warning: Following experimental annotations are used:
> - **OptIn**
>   - `androidx.compose.ui.ExperimentalComposeUiApi` (1x)
>
> I try to use as less experimental features as possible, but in this case the ones above are needed!

# :wrench: Setup

<details open>

<summary><b>Using Version Catalogs</b></summary>

<br>

Define the dependencies inside your **libs.versions.toml** file.

```toml
[versions]

composepreferences = "<LATEST-VERSION>"

[libraries]

composepreferences-core = { module = "io.github.mflisar.composepreferences:core", version.ref = "composepreferences" }
composepreferences-screen-bool = { module = "io.github.mflisar.composepreferences:screen-bool", version.ref = "composepreferences" }
composepreferences-screen-button = { module = "io.github.mflisar.composepreferences:screen-button", version.ref = "composepreferences" }
composepreferences-screen-color = { module = "io.github.mflisar.composepreferences:screen-color", version.ref = "composepreferences" }
composepreferences-screen-date = { module = "io.github.mflisar.composepreferences:screen-date", version.ref = "composepreferences" }
composepreferences-screen-input = { module = "io.github.mflisar.composepreferences:screen-input", version.ref = "composepreferences" }
composepreferences-screen-list = { module = "io.github.mflisar.composepreferences:screen-list", version.ref = "composepreferences" }
composepreferences-screen-number = { module = "io.github.mflisar.composepreferences:screen-number", version.ref = "composepreferences" }
composepreferences-screen-time = { module = "io.github.mflisar.composepreferences:screen-time", version.ref = "composepreferences" }
composepreferences-kotpreferences = { module = "io.github.mflisar.composepreferences:kotpreferences", version.ref = "composepreferences" }
```

And then use the definitions in your projects **build.gradle.kts** file like following:

```java
implementation(libs.composepreferences.core)
implementation(libs.composepreferences.screen.bool)
implementation(libs.composepreferences.screen.button)
implementation(libs.composepreferences.screen.color)
implementation(libs.composepreferences.screen.date)
implementation(libs.composepreferences.screen.input)
implementation(libs.composepreferences.screen.list)
implementation(libs.composepreferences.screen.number)
implementation(libs.composepreferences.screen.time)
implementation(libs.composepreferences.kotpreferences)
```

</details>

<details>

<summary><b>Direct Dependency Notation</b></summary>

<br>

Simply add the dependencies inside your **build.gradle.kts** file.

```kotlin
val composepreferences = "<LATEST-VERSION>"

implementation("io.github.mflisar.composepreferences:core:${composepreferences}")
implementation("io.github.mflisar.composepreferences:screen-bool:${composepreferences}")
implementation("io.github.mflisar.composepreferences:screen-button:${composepreferences}")
implementation("io.github.mflisar.composepreferences:screen-color:${composepreferences}")
implementation("io.github.mflisar.composepreferences:screen-date:${composepreferences}")
implementation("io.github.mflisar.composepreferences:screen-input:${composepreferences}")
implementation("io.github.mflisar.composepreferences:screen-list:${composepreferences}")
implementation("io.github.mflisar.composepreferences:screen-number:${composepreferences}")
implementation("io.github.mflisar.composepreferences:screen-time:${composepreferences}")
implementation("io.github.mflisar.composepreferences:kotpreferences:${composepreferences}")
```

</details>

# :rocket: Usage

#### Basic example

```kotlin
// select a style for your preferences
val style = DefaultStyle.create()
val modernStyle = ModernStyle.create()

// create a preference settings instance (you can adjust a few additional settings here)
val settings = PreferenceSettingsDefaults.settings(
    style = style
)

// create a state for the preference screen (this is optional - only needed if you need access to informations from this state)
val state = rememberPreferenceState()

// create a preference screen
PreferenceScreen(
    modifier = Modifier,
    settings = settings,
    state = state
) {
    // preference items
    // ...
}
```

#### Preference items

Check out the modules region in the menu on the left to find out more about the different preference items.

Here's a very basic example to show you how the preference items are used inside the `PreferenceScreen`:

```kotlin
PreferenceScreen(
    // ...
) {
    PreferenceSection(
        title = "Section 1"
    ) {
        PreferenceInfo(
            title = "Info 1"
        )
        val checked = remember { mutableStateOf(false) }
        PreferenceBool(
            value = checked,
            title = "Boolean Preference"
        )
        val input =  remember { mutableStateOf("") }
        PreferenceInputText(
            value = input,
            title = "Input Preference"
        )
    }
    PreferenceSection(
        title = "Section 2"
    ) {
        PreferenceInfo(
            title = "Info 2"
        )
    }
}
```

# :sparkles: Demo

A [demo project](demo) is included.

# :books: API

Check out the [API documentation](https://MFlisar.github.io/ComposePreferences/api/).

# :bulb: Other Libraries

You can find more of my multiplatform libraries that work well together [here](https://mflisar.github.io/Libraries/).

# :twisted_rightwards_arrows: Compatibility

When combining my libraries, you can find compatibility information [here](https://mflisar.github.io/Libraries/compatibilities/).
