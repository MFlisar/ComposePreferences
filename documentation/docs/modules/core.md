---
icon: material/puzzle
---

This is the core module. It contains all the basic preference stuff like `styles`, `sections`, `groups`, `hierarchy management`, etc...

The general usage is already described in the usage region. In the following I shortly describe how the main composables work. This module also contains all classes that are needed for advanced usage - check out the advanced section for more details.

## PreferenceInfo

This shows a simple information preference.

```kotlin
--8<-- "../library/core/src/commonMain/kotlin/com/michaelflisar/composepreferences/core/PreferenceInfo.kt:constructor"
```

## PreferenceDivider

This shows a simple divider preference.

```kotlin
--8<-- "../library/core/src/commonMain/kotlin/com/michaelflisar/composepreferences/core/PreferenceDivider.kt:constructor"
```

## PreferenceSection

This allows you to wrap a group of preferences in a section. Depending on the style this may also change the visual appearance of the preferences.

=== "Inside a `PreferenceRootScope`"

    ```kotlin
    --8<-- "../library/core/src/commonMain/kotlin/com/michaelflisar/composepreferences/core/PreferenceSection.kt:constructor"
    ```

=== "Inside a `PreferenceGroupScope`"

    ```kotlin
    --8<-- "../library/core/src/commonMain/kotlin/com/michaelflisar/composepreferences/core/PreferenceSection.kt:constructor2"
    ```

## PreferenceSubScreen

This allows you to wrap a group of preferences in a sub screen. This means, that all children will only be visible if the user clicks on the preference. This will then open the sub screen and show it's content.

```kotlin
--8<-- "../library/core/src/commonMain/kotlin/com/michaelflisar/composepreferences/core/PreferenceSubScreen.kt:constructor"
```




