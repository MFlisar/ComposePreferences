---
icon: material/link
---

All preferences allow you to enable/disable and show/hide them based on dependencies. This allows you to create complex preference screens with dependencies between preferences.

#### Example

Following shows a simple example where the second preference directly depends on the first preference. The dependency is used for the enabled state.

```kotlin
--8<-- "../../demo/shared/src/commonMain/kotlin/com/michaelflisar/composepreferences/demo/demos/PrefScreenDemo.kt:demo-dependency"
```

Using it for the visibility state works the same way.

```kotlin
--8<-- "../../demo/shared/src/commonMain/kotlin/com/michaelflisar/composepreferences/demo/demos/PrefScreenDemo.kt:demo-dependency2"
```

#### Depending on arbitrary data

The dependency class allows you to depend on any data, you just have to convert the data to a boolean. Following shows an example where the dependency depends on an `Int` state and derives the enabled state from it.

```kotlin
--8<-- "../../demo/shared/src/commonMain/kotlin/com/michaelflisar/composepreferences/demo/demos/PrefScreenDemo.kt:demo-dependency3a"
--8<-- "../../demo/shared/src/commonMain/kotlin/com/michaelflisar/composepreferences/demo/demos/PrefScreenDemo.kt:demo-dependency3b"
```
