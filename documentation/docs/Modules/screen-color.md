---
icon: material/puzzle
---

|                                                   |                                                   |
|---------------------------------------------------|---------------------------------------------------|
| ![Screenshot](../screenshots/previews/color1.jpg) | ![Screenshot](../screenshots/previews/color2.jpg) |

This shows a simple color picker preference.

Check out the composable and it's documentation in the code snipplet below.

#### Example

```kotlin
--8<-- "../../demo/app/android/src/main/java/com/michaelflisar/composepreferences/demo/demos/PrefScreenDemo.kt:demo-color"
```

#### Composable

=== "Data as `MutableState`"

    ```kotlin
    --8<-- "../../library/modules/screen/color/src/commonMain/kotlin/com/michaelflisar/composepreferences/screen/color/PreferenceColor.kt:constructor"
    ```

=== "Data as `value` + `onValueChange`"

    ```kotlin
    --8<-- "../../library/modules/screen/color/src/commonMain/kotlin/com/michaelflisar/composepreferences/screen/color/PreferenceColor.kt:constructor2"
    ```

#### Screenshots

|                                                       |                                                       |
|-------------------------------------------------------|-------------------------------------------------------|
| ![Screenshot](../screenshots/color/color-default.jpg) | ![Screenshot](../screenshots/color/color-modern.jpg)  |
| ![Screenshot](../screenshots/color/color-dialog.jpg)  | ![Screenshot](../screenshots/color/color-dialog2.jpg) |