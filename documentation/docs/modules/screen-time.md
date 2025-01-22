---
icon: material/puzzle
---

This shows a simple timeean preference. It allows to toggle a timeean value.

Check out the composable and it's documentation in the code snipplet below.

#### Example

```kotlin
--8<-- "../demo/android/src/main/java/com/michaelflisar/composepreferences/demo/demos/PrefScreenDemo.kt:demo-time"
```

#### Composable

=== "Data as `MutableState`"

    ```kotlin
    --8<-- "../library/modules/screen/time/src/commonMain/kotlin/com/michaelflisar/composepreferences/screen/time/PreferenceTime.kt:constructor"
    ```

=== "Data as `value` + `onValueChange`"

    ```kotlin
    --8<-- "../library/modules/screen/time/src/commonMain/kotlin/com/michaelflisar/composepreferences/screen/time/PreferenceTime.kt:constructor2"
    ```

#### Screenshots

|                                              |                                              |
|----------------------------------------------|----------------------------------------------|
| ![Screenshot](../screenshots/time/time1.jpg) | ![Screenshot](../screenshots/time/time2.jpg) |