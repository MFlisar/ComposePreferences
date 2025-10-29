---
icon: material/puzzle
---

|                                                  |
|--------------------------------------------------|
| ![Screenshot](../screenshots/previews/time1.jpg) | 

This shows a simple timeean preference. It allows to toggle a timeean value.

Check out the composable and it's documentation in the code snipplet below.

#### Example

```kotlin
--8<-- "../../demo/shared/src/commonMain/kotlin/com/michaelflisar/composepreferences/demo/demos/PrefScreenDemo.kt:demo-time"
```

#### Composable

=== "Data as `MutableState`"

    ```kotlin
    --8<-- "../../library/modules/screen/time/src/commonMain/kotlin/com/michaelflisar/composepreferences/screen/time/PreferenceTime.kt:constructor"
    ```

=== "Data as `value` + `onValueChange`"

    ```kotlin
    --8<-- "../../library/modules/screen/time/src/commonMain/kotlin/com/michaelflisar/composepreferences/screen/time/PreferenceTime.kt:constructor2"
    ```

#### Screenshots

|                                                     |                                                    |
|-----------------------------------------------------|----------------------------------------------------|
| ![Screenshot](../screenshots/time/time-default.jpg) | ![Screenshot](../screenshots/time/time-modern.jpg) |
| ![Screenshot](../screenshots/time/time-dialog.jpg)  |                                                    |