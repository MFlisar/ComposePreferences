---
icon: material/puzzle
---

|                                                  |
|--------------------------------------------------|
| ![Screenshot](../screenshots/previews/date1.jpg) |

This shows a simple date picker preference.

Check out the composable and it's documentation in the code snipplet below.

#### Example

```kotlin
--8<-- "../../demo/android/src/main/java/com/michaelflisar/composepreferences/demo/demos/PrefScreenDemo.kt:demo-date"
```

#### Composable

=== "Data as `MutableState`"

    ```kotlin
    --8<-- "../../library/modules/screen/date/src/commonMain/kotlin/com/michaelflisar/composepreferences/screen/date/PreferenceDate.kt:constructor"
    ```

=== "Data as `value` + `onValueChange`"

    ```kotlin
    --8<-- "../../library/modules/screen/date/src/commonMain/kotlin/com/michaelflisar/composepreferences/screen/date/PreferenceDate.kt:constructor2"
    ```

#### Screenshots

|                                                     |                                                    |
|-----------------------------------------------------|----------------------------------------------------|
| ![Screenshot](../screenshots/date/date-default.jpg) | ![Screenshot](../screenshots/date/date-modern.jpg) |
| ![Screenshot](../screenshots/date/date-dialog.jpg)  |                                                    |