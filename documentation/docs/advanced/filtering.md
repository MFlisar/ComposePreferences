---
icon: material/filter
---

Filtering is an optional feature. It allows to filter the preferences and render the result as a flat list or just filters and still keeps the hierarchy intact.

#### Setup

You just have to define a filter mode...

```kotlin
--8<-- "../demo/desktop/src/jvmMain/kotlin/com/michaelflisar/composepreferences/demo/PrefScreenDemoFilter.kt:filter-modes"
```

... then create a filter setup ...

```kotlin
--8<-- "../demo/desktop/src/jvmMain/kotlin/com/michaelflisar/composepreferences/demo/PrefScreenDemoFilter.kt:filter"
```

... and finally pass on the filter to the screen:

```kotlin
--8<-- "../demo/desktop/src/jvmMain/kotlin/com/michaelflisar/composepreferences/demo/PrefScreenDemoFilter.kt:filter2"
```

#### Filter

Afterwards simple adjust the `filter.search` state and the screen will automatically update the list of preferences based on your filter settings.

```kotlin
--8<-- "../demo/desktop/src/jvmMain/kotlin/com/michaelflisar/composepreferences/demo/PrefScreenDemoFilter.kt:filter-input"
```

#### Other customisations

Additionally the `filter` object does provide some states to make more adjustments if desired.

```kotlin
--8<-- "../library/core/src/commonMain/kotlin/com/michaelflisar/composepreferences/core/filter/DefaultPreferenceFilter.kt:constructor"
```

### Custom filter

If desired, you can implement a full custom implementation of the `PreferenceFilter` interface. Just check out the `DefaultPreferenceFilter` to find an example on how this is done.


