---
icon: material/keyboard
---

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
        val input =  remember { mutableStateOf(false) }
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